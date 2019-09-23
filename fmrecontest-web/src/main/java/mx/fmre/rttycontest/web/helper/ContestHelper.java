package mx.fmre.rttycontest.web.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.web.dto.ContestReturnDTO;

@Component
public class ContestHelper {
	public List<ContestReturnDTO> parse(List<Contest> listContest) {
		return listContest.stream().map(c -> {
			ContestReturnDTO dto = new ContestReturnDTO();
			dto.setId(c.getId());
			dto.setDescripcion(c.getDescription());
			return dto;
		}).collect(Collectors.toList());
	}
}
