package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.dto.BandDto;
import mx.fmre.rttycontest.api.service.IBandService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.repository.ICatBandRepository;

@Service
public class BandServiceImpl implements IBandService {
	
	@Autowired ICatBandRepository catBandRepository;

	@Override
	public List<BandDto> getAll() throws FmreContestException {
		return catBandRepository
				.findAll()
				.stream()
				.map(x -> this.map(x))
				.collect(Collectors.toList());
				
	}
	
	private BandDto map(CatBand catBand) {
		BandDto bandDto = new BandDto();
		bandDto.setId(catBand.getId());
		bandDto.setBand(catBand.getBand());
		return bandDto;
	}
	
}
