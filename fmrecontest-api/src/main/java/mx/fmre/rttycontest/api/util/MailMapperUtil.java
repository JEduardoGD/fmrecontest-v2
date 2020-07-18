package mx.fmre.rttycontest.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import mx.fmre.rttycontest.dto.EmailDTO;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailStatus;

public abstract class MailMapperUtil {

	public static EmailDTO map(Email e, EmailStatus emailStatus, List<AttachedFile> attachedFiles) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setSubject(e.getSubject());
		emailDTO.setSentDate(df.format(e.getSentDate()));
		emailDTO.setReceivedDate(df.format(e.getReceivedDate()));
		emailDTO.setId(e.getId());
		emailDTO.setRecipientsFromAddress(e.getRecipientsFromAddress());
		emailDTO.setRecipientsFromName(e.getRecipientsFromName());
		emailDTO.setEmailStatus(emailStatus.getStatus());
		emailDTO.setAtachedFiles(AttachedFileUtil.map(attachedFiles));
		return emailDTO;

	}
}
