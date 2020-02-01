package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.dto.ContestDTO;
import mx.fmre.rttycontest.api.service.IContestService;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;

@Service
public class ContestServiceImpl implements IContestService {

	@Autowired
	IContestRepository contestRepository;

	@Override
	public List<ContestDTO> getAll() {
		return contestRepository.findAll().stream().map(c -> {
			ContestDTO dto = new ContestDTO();
			dto.setId(c.getId());
			dto.setDescription(c.getDescription());
			return dto;
		}).collect(Collectors.toList());
	}
}
