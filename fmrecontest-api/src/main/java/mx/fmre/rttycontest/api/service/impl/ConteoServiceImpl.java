package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.dto.ConteoDto;
import mx.fmre.rttycontest.api.service.IConteoService;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;

@Service
public class ConteoServiceImpl implements IConteoService {

	@Autowired
	private IConteoRepository conteoRepository;

	@Override
	public List<ConteoDto> getAll() {
		return conteoRepository.findAll().stream().map(c -> {
			ConteoDto conteoDto = new ConteoDto();
			conteoDto.setId(c.getId());
			conteoDto.setDescription(c.getDescription());
			conteoDto.setDatetime(c.getDatetime());
			conteoDto.setEditionId(c.getEdition().getId());
			return conteoDto;
		}).collect(Collectors.toList());
	}

}
