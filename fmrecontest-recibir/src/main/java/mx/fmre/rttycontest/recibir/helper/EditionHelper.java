package mx.fmre.rttycontest.recibir.helper;

import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.Edition;

@Service
public class EditionHelper {
	public String createBucketName(Edition edition) {
		Integer contestId = edition.getContest().getId();
		Integer editionId = edition.getId();
		return "contest_" + contestId.intValue() + "/" + "edition_" + editionId.intValue();
	}
}
