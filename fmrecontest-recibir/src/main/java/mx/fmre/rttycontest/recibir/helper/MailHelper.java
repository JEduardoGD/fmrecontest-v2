package mx.fmre.rttycontest.recibir.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import mx.fmre.rttycontest.bs.dto.AttachedFileDTO;
import mx.fmre.rttycontest.bs.util.FileUtil;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailStatus;

public class MailHelper {
	private MailHelper() {
		// not called
	}

	public static List<AttachedFileDTO> getAttachedFiles(Message message) throws IOException, MessagingException {
		Object content = message.getContent();
		if (content instanceof Multipart) {
			Multipart multipart = (Multipart) message.getContent();

			List<AttachedFileDTO> listAttachedFileDTO = new ArrayList<>();
			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())
						&& StringUtils.isBlank(bodyPart.getFileName())) {
					continue; // dealing with attachments only
				}
				try (InputStream is = bodyPart.getInputStream()) {
					byte[] byteArray = FileUtil.inputStreamToByteArray(is);
					String contentType = bodyPart.getContentType();
					
					String filename = bodyPart.getFileName();//=?UTF-8?b?ZHUzbGEgeGUgcnR0eSAyMDE5?=
					if(filename.startsWith("=?UTF-8?b?"))
						filename = FileUtil.base64ToString(filename.replace("=?UTF-8?b?", "").replace("MDE5?=", ""));
					
					AttachedFileDTO attachedFileDTO = new AttachedFileDTO();
					attachedFileDTO.setFilename(filename);
					attachedFileDTO.setByteArray(byteArray);
					attachedFileDTO.setContenyType(contentType);
					attachedFileDTO.setLenght(bodyPart.getSize());
					attachedFileDTO.setHash(FileUtil.getMd5Hash(byteArray));

					listAttachedFileDTO.add(attachedFileDTO);
				}
			}
			return listAttachedFileDTO;
		}
		return new ArrayList<>();
	}

	public static AttachedFile attachedFileDTOToAttachedFile(AttachedFileDTO attachedFileDTO) {
		AttachedFile attachedFile = new AttachedFile();
		attachedFile.setFilename(attachedFileDTO.getFilename());
		attachedFile.setContentType(attachedFileDTO.getContenyType());
		attachedFile.setLenght(attachedFileDTO.getLenght());
		attachedFile.setMd5Hash(attachedFileDTO.getHash());
		attachedFile.setPath(attachedFileDTO.getPath());
		attachedFile.setLogFile(false);
		return attachedFile;
	}

	public static Email messageToEmailMapper(Edition edition, Message message, int toFieldLenght,
			EmailStatus emailStatus) throws MessagingException {
		ParsedAddressDTO parsedAddressDTO = parseAddress(message.getFrom()[0]);
		Email email = new Email();
		email.setEdition(edition);
		email.setEmailCount(message.getMessageNumber());
		email.setRecipientsFromName(parsedAddressDTO.getPersonal());
		email.setRecipientsFromAddress(parsedAddressDTO.getEmailAddress());
		email.setReceivedDate(message.getReceivedDate());
		email.setRecipientsTo(stringAddresses(message.getRecipients(RecipientType.TO), toFieldLenght));
		email.setSentDate(message.getSentDate());
		email.setSubject(message.getSubject());
		email.setEmailStatus(emailStatus);
		email.setVerifiedAt(null);
		email.setAnsweredAt(null);
		return email;
	}

	private static ParsedAddressDTO parseAddress(Address address) {
		InternetAddress internetAddress = (InternetAddress) address;
		ParsedAddressDTO parsedAddressDTO = new ParsedAddressDTO();
		parsedAddressDTO.setPersonal(internetAddress.getPersonal() != null ? internetAddress.getPersonal() : null);
		parsedAddressDTO.setEmailAddress(internetAddress.getAddress() != null ? internetAddress.getAddress() : null);
		return parsedAddressDTO;
	}

	private static String stringAddresses(Address[] addresses, int addressesFieldLenght) {
		if (addresses == null || addresses.length <= 0)
			return null;
		StringBuilder sb = new StringBuilder();
		for (Address address : addresses) {
			InternetAddress addressInternetAddress = (InternetAddress) address;
			String emailAddress = addressInternetAddress.getAddress();
			String personal = addressInternetAddress.getPersonal();
			StringBuilder sbTemp = new StringBuilder();
			if (personal != null)
				sbTemp.append(personal).append(" ").append("<").append(emailAddress).append(">").append(",");
			else
				sbTemp.append(emailAddress).append(",");

			if ((sb.toString() + sbTemp.toString()).length() > addressesFieldLenght)
				break;
			else
				sb.append(sbTemp.toString());

		}
		String newString = sb.toString();
		return newString.substring(0, newString.length() - 1);
	}
}

@Data
class ParsedAddressDTO {
	private String personal;
	private String emailAddress;
}