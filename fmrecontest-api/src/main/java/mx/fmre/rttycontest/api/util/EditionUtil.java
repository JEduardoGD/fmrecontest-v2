package mx.fmre.rttycontest.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import mx.fmre.rttycontest.dto.EditionDTO;
import mx.fmre.rttycontest.persistence.model.Edition;

public abstract class EditionUtil {
	private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static EditionDTO parse(Edition edition, String contestDescription) {
		EditionDTO editionDTO = new EditionDTO();
		editionDTO.setId(edition.getId());
		editionDTO.setDescription(edition.getDescription());
		editionDTO.setYear(edition.getYear());
		editionDTO.setStartDate(df.format(edition.getStart()));
		editionDTO.setEndDate(df.format(edition.getEnd()));
		editionDTO.setContest(contestDescription);
		return editionDTO;
	}
}
