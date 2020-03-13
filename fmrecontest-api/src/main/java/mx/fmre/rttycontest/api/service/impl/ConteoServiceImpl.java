package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.dto.ConteoDto;
import mx.fmre.rttycontest.api.service.IConteoService;
import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IRelConteoContestLogRepository;

@Service
public class ConteoServiceImpl implements IConteoService {

	@Autowired private IConteoRepository conteoRepository;
	@Autowired private IEditionRepository editionRepository;
	@Autowired private IContestRepository contestRepository;
	@Autowired private IRelConteoContestLogRepository relConteoContestLogRepository;
	
	private List<Edition> editionList;
	private List<Contest> contestList;
	
	@PostConstruct
	private void load() {
		this.editionList = editionRepository.findAll();
		this.contestList = contestRepository.findAll();
	}

	@Override
	public List<ConteoDto> getAll() {
		return conteoRepository.findAll().stream().map(c -> {
			List<RelConteoContestLog> relConteoContestLogList = relConteoContestLogRepository.findByConteo(c);
			int countNoComplete = relConteoContestLogList
					.stream()
					.filter(x -> x.isComplete() == false)
					.collect(Collectors.toList())
					.size();
			
			Edition edition = editionList
					.stream()
					.filter(e -> e.getId().equals(c.getEdition().getId()))
					.findFirst()
					.orElse(null);
			
			Contest contest = contestList
					.stream()
					.filter(co -> co.getId().equals(edition.getContest().getId()))
					.findFirst()
					.orElse(null);
			
			ConteoDto conteoDto = new ConteoDto();
			conteoDto.setId(c.getId());
			conteoDto.setDescription(c.getDescription());
			conteoDto.setDatetime(c.getDatetime());
			conteoDto.setEditionId(c.getEdition().getId());
			conteoDto.setNoComplete(countNoComplete > 0);
			conteoDto.setEditionId(edition.getId());
			conteoDto.setEdition(edition.getDescription());
			conteoDto.setContestId(contest.getId());
			conteoDto.setContest(contest.getDescription());
			return conteoDto;
		}).collect(Collectors.toList());
	}

}
