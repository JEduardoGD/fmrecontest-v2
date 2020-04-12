package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.dto.FrequencyDTO;
import mx.fmre.rttycontest.api.service.IFrequencyApiService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.CatFrequencyBand;
import mx.fmre.rttycontest.persistence.repository.ICatBandRepository;
import mx.fmre.rttycontest.persistence.repository.ICatFrequencyBandRepository;

@Service
public class FrequencyApiServiceImpl implements IFrequencyApiService {
	
	@Autowired private ICatFrequencyBandRepository catFrequencyBandRepository;
	@Autowired private ICatBandRepository catBandRepository;
	
	private List<CatBand> catBandList;
	
	@PostConstruct
	private void loadBands() {
		this.catBandList = catBandRepository.findAll();
	}

	@Override
	public List<FrequencyDTO> getAll() throws FmreContestException {
		List<CatFrequencyBand> catFrequencyBand = catFrequencyBandRepository.findAll();
		return catFrequencyBand.stream().map(x-> this.map(x)).collect(Collectors.toList());
	}
	
	public FrequencyDTO map(CatFrequencyBand catFrequencyBand) {
		CatBand band = catBandList
		.stream()
		.filter(x -> (x.getId().intValue() == catFrequencyBand.getBand().getId().intValue()))
				.findFirst().orElse(null);
		FrequencyDTO frequencyDTO = new FrequencyDTO();
		frequencyDTO.setId(catFrequencyBand.getId());
		frequencyDTO.setBand(band.getBand());
		return frequencyDTO;
	}
}
