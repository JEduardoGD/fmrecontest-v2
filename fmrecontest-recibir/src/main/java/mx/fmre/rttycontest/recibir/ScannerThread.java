package mx.fmre.rttycontest.recibir;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.crypto.NoSuchPaddingException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dto.AttachedFileDTO;
import mx.fmre.rttycontest.bs.util.FileUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailAccount;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.helper.EncryptDecryptStringHelper;
import mx.fmre.rttycontest.recibir.helper.MailHelper;
import mx.fmre.rttycontest.recibir.services.IFileManagerService;

@Slf4j
@AllArgsConstructor
public class ScannerThread {

	private Edition edition;
	private IContestRepository contestRepository;
	private String emailPasswordEncodingkey;
	private IEmailRepository emailRepository;
	private int emailFieldsToLenght;
	private IFileManagerService fileManagerService;
	private EmailStatus emailEstatusRecived;
	private Integer messagesPerminute;

	private final String UTF8_ENCODEDFILENAME_PATTERN = "\\=\\?(UTF-8|utf-8)\\?(B|b)\\?";

	public void run() {
		try {
			this.scan();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | MessagingException
				| IOException | FmreContestException e) {
			log.error(e.getLocalizedMessage());
		}
	}

	private void scan() throws MessagingException, IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, FmreContestException {
		Contest contest = contestRepository.findById(edition.getContest().getId()).orElse(null);
		EncryptDecryptStringHelper encryptDecryptStringHelper = null;
		if (contest == null)
			return;
		EmailAccount emailAccount = contest.getEmailAccount();
		encryptDecryptStringHelper = new EncryptDecryptStringHelper(emailPasswordEncodingkey);

		Session session = Session.getDefaultInstance(new Properties());
		Store store = session.getStore("imaps");
		store.connect(emailAccount.getInHost(), emailAccount.getInPort(), emailAccount.getEmailAddress(),
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
		int messageCount = inbox.getMessageCount();
		
		int maxIdEmail = messageCount > maxIdEmailSaved ? messageCount : maxIdEmailSaved;
		
		List<Integer> shouldBeSaved = IntStream
				.iterate(edition.getEmailStart(), x -> x + 1)
				.limit(maxIdEmail - edition.getEmailStart() + 1)
				.boxed()
				.collect(Collectors.toList());
		shouldBeSaved.removeAll(saved);
		
		if(shouldBeSaved.isEmpty())
			return;
		
		int upperLimit = messagesPerminute < shouldBeSaved.size() ? messagesPerminute : shouldBeSaved.size();
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
			Email email = MailHelper.messageToEmailMapper(edition, message, emailFieldsToLenght, emailEstatusRecived);
			List<AttachedFile> attachedFiles = new ArrayList<>();
			for (AttachedFileDTO attachedFileDTO : attachedFilesDTO) {
				attachedFileDTO.setFilename(parseBase64encodedFilename(attachedFileDTO.getFilename()));
				
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
	
	private String parseBase64encodedFilename(String filename) {
		Pattern p = Pattern.compile("^" + UTF8_ENCODEDFILENAME_PATTERN + ".*" + "$");
		Matcher m = p.matcher(filename);
		if (m.matches()) {
			String[] arr = filename.split(UTF8_ENCODEDFILENAME_PATTERN);
			if (arr.length == 2) {
				try {
					return FileUtil.mimeBase64ToString(arr[1]);
				} catch (Exception e) {
					log.error(e.getLocalizedMessage());
					return null;
				}
			}
		}
		return filename;
	}
}
