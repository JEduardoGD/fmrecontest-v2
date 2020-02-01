package mx.fmre.rttycontest.api.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.service.IEditionService;
import mx.fmre.rttycontest.dto.EditionDTO;
import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;

@Service
public class EditionServiceImpl implements IEditionService {

	@Autowired
	private IEditionRepository editionRepository;
	@Autowired
	private IContestRepository contestRepository;
	
	private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Map<Integer, Contest> m = new HashMap<>(); 

	@Override
	public List<EditionDTO> getAllByContestId(Integer contestId) {
		return editionRepository.getByContestId(contestId)
				.stream()
				.map(x -> {
			EditionDTO editionDTO = new EditionDTO();
			editionDTO.setId(x.getId());
			editionDTO.setDescription(x.getDescription());
			editionDTO.setYear(x.getYear());
			editionDTO.setStartDate(df.format(x.getStart()));
			editionDTO.setEndDate(df.format(x.getEnd()));
			
			Contest contest = m.get(x.getContest().getId());
			contest = contest != null ? contest : contestRepository.findById(x.getContest().getId()).orElse(null);
			m.put(contest.getId(), contest);
			editionDTO.setContest(contest.getDescription());
			
			return editionDTO;
		}).collect(Collectors.toList());
	}

}
