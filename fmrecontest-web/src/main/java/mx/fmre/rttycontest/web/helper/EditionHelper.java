package mx.fmre.rttycontest.web.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.web.dto.EditionReturnDTO;

@Component
public class EditionHelper {
	public List<EditionReturnDTO> parse(List<Edition> listContest) {
		return listContest.stream().map(c -> {
			EditionReturnDTO r = new EditionReturnDTO();
			r.setId(c.getId());
			r.setContestDescription(c.getContest().getDescription());
			r.setDescription(c.getDescription());
			r.setYear(c.getYear());
			r.setStart(new java.sql.Timestamp(c.getStart().getTime()).toLocalDateTime());
			r.setEnd(new java.sql.Timestamp(c.getEnd().getTime()).toLocalDateTime());
			return r;
		}).collect(Collectors.toList());
	}
}
