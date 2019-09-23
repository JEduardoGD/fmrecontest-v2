package mx.fmre.rttycontest.recibir;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.crypto.NoSuchPaddingException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import lombok.AllArgsConstructor;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailAccount;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.dto.AttachedFileDTO;
import mx.fmre.rttycontest.recibir.helper.EncryptDecryptStringHelper;
import mx.fmre.rttycontest.recibir.helper.MailHelper;
import mx.fmre.rttycontest.recibir.services.IFileManagerService;

@AllArgsConstructor
public class ScannerThread implements Runnable{

	private Edition edition;
	private IContestRepository contestRepository;
	private String emailPasswordEncodingkey;
	private IEmailRepository emailRepository;
	private int emailFieldsToLenght;
	private IFileManagerService fileManagerService;

	@Override
	public void run() {
		try {
			this.scan();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | MessagingException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void scan() throws MessagingException, IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException {
		Contest contest = contestRepository.findById(edition.getContest().getId()).orElse(null);
		EncryptDecryptStringHelper encryptDecryptStringHelper = null;
		if (contest == null)
			return;
		EmailAccount emailAccount = contest.getEmailAccount();
		encryptDecryptStringHelper = new EncryptDecryptStringHelper(emailPasswordEncodingkey);

		Session session = Session.getDefaultInstance(new Properties());
		Store store = session.getStore("imaps");
		store.connect(emailAccount.getSmtpServer(), emailAccount.getPort(), emailAccount.getEmailAddress(),
				encryptDecryptStringHelper.decrypt(emailAccount.getPassword()));
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);

		List<Integer> saved;
		if (edition.getEmailEnd() != null)
			saved = emailRepository.getEmailCountsSaved(edition.getEmailStart(), edition.getEmailEnd(),
					edition.getId());
		else
			saved = emailRepository.getEmailCountsSaved(edition.getEmailStart(), edition.getId());
		int maxIdEmailSaved = saved
			      .stream()
			      .mapToInt(v -> v)
			      .max()
			      .orElse(0);
			      //.orElseThrow(NoSuchElementException::new);
		int messageCount = inbox.getMessageCount();
		
		int maxIdEmail = messageCount > maxIdEmailSaved ? messageCount : maxIdEmailSaved;
		
		List<Integer> shouldBeSaved = IntStream
				.iterate(edition.getEmailStart(), x -> x + 1)
				.limit(maxIdEmail - edition.getEmailStart() + 1)
				.boxed()
				.collect(Collectors.toList());
		shouldBeSaved.removeAll(saved);
		
		if(shouldBeSaved.size() <= 0)
			return;
		
		int upperLimit = 10 < shouldBeSaved.size() ? 10 : shouldBeSaved.size();
		List<Integer> listToDownload = shouldBeSaved.subList(0, upperLimit);
		int[] intArray = new int[listToDownload.size()];
		for (int i = 0; i < listToDownload.size(); i++) {
			intArray[i] = listToDownload.get(i);
		}

		// Fetch unseen messages from inbox folder
//		Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
//		int messageCount = inbox.getMessageCount();
		Message[] messages = inbox.getMessages(intArray);

		for (Message message : messages) {
			List<AttachedFileDTO> attachedFilesDTO = MailHelper.getAttachedFiles(message);
			Email email = MailHelper.messageToEmailMapper(edition, message, emailFieldsToLenght);
			List<AttachedFile> attachedFiles = new ArrayList<>();
			for (AttachedFileDTO attachedFileDTO : attachedFilesDTO) {
				AttachedFile attachedFile = MailHelper.attachedFileDTOToAttachedFile(attachedFileDTO);
				attachedFile.setEmail(email);
				String bucketPath = fileManagerService.saveFile(email, attachedFileDTO);
				attachedFile.setPath(bucketPath);
				attachedFiles.add(attachedFile);
			}
			email.setAttachedFiles(attachedFiles);
			emailRepository.save(email);
		}

	}
}
