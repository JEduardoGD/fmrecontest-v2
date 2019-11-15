package mx.fmre.rttycontest.web.helper;

import java.util.List;
import java.util.stream.Collectors;

import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.web.dto.EmailReturnDTO;

public class EmailHelper {
	public static List<EmailReturnDTO> parse(List<Email> listContest) {
		return listContest.stream().map(c -> {
			EmailReturnDTO r = new EmailReturnDTO();
			
			String from = "";
			
			if(c.getRecipientsFromName() != null && c.getRecipientsFromAddress() != null)
				from  = String.format("\"%s\" <%s>", c.getRecipientsFromName(), c.getRecipientsFromAddress());

			if(c.getRecipientsFromName() != null && c.getRecipientsFromAddress() == null)
				from  = String.format("%s", c.getRecipientsFromName());
			
			if(c.getRecipientsFromName() == null && c.getRecipientsFromAddress() != null)
				from  = String.format("%s", c.getRecipientsFromAddress());
			
			r.setIdEmail(c.getId());
			r.setEmailCount(c.getEmailCount());
			r.setIdEdition(c.getEdition().getId());
			r.setFrom(from);
			r.setReceivedDate(c.getReceivedDate().toString());
			r.setSentDate(c.getSentDate().toString());
			r.setSubject(c.getSubject());
			
			return r;
		})
				.sorted((o1,o2)-> o2.getReceivedDate().compareTo(o1.getReceivedDate()))
				.collect(Collectors.toList());
	}
}
