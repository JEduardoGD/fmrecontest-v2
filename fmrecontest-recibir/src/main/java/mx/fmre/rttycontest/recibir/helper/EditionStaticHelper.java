package mx.fmre.rttycontest.recibir.helper;

import mx.fmre.rttycontest.bs.dto.AttachedFileDTO;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;

public class EditionStaticHelper {
	private EditionStaticHelper() {
		// not called
	}
	
	public static String filename(Email email, Edition edition, AttachedFileDTO fileDTO) {
		return String.format("contest_%d/edition_%d/emailcount_%d/%s", 
				edition.getContest().getId(), 
				edition.getId(),
				email.getEmailCount(), 
				fileDTO.getFilename());
	}

	public static String createBucketName(Edition edition) {
		Integer contestId = edition.getContest().getId();
		Integer editionId = edition.getId();
		return "contest_" + contestId.intValue() + "/" + "edition_" + editionId.intValue();
	}
}
