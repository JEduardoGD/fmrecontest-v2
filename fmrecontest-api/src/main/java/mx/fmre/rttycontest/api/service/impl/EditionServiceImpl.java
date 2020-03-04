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
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;

@Service
public class EditionServiceImpl implements IEditionService {

	@Autowired private IEditionRepository editionRepository;
	@Autowired private IContestRepository contestRepository;
	
	private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Map<Integer, Contest> m = new HashMap<>(); 

	@Override
	public List<EditionDTO> getAllByContestId(Integer contestId) {
		return editionRepository.getByContestId(contestId)
				.stream()
				.map(x -> {
					return this.map(x);
		}).collect(Collectors.toList());
	}

	@Override
	public List<EditionDTO> getAll() {
		return editionRepository.findAll()
				.stream()
				.map(x -> {
					return this.map(x);
		}).collect(Collectors.toList());
	}
	
	private EditionDTO map(Edition edition) {
		EditionDTO editionDTO = new EditionDTO();
		editionDTO.setId(edition.getId());
		editionDTO.setDescription(edition.getDescription());
		editionDTO.setYear(edition.getYear());
		editionDTO.setStartDate(df.format(edition.getStart()));
		editionDTO.setEndDate(df.format(edition.getEnd()));
		
		Contest contest = m.get(edition.getContest().getId());
		contest = contest != null ? contest : contestRepository.findById(edition.getContest().getId()).orElse(null);
		m.put(contest.getId(), contest);
		editionDTO.setContest(contest.getDescription());
		
		return editionDTO;
	}

}
