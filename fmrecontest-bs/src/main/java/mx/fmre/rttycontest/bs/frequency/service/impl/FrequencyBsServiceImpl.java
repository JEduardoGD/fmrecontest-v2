package mx.fmre.rttycontest.bs.frequency.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.frequency.service.IFrequencyBsService;
import mx.fmre.rttycontest.bs.util.CollectiosUtil;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.CatFrequencyBand;
import mx.fmre.rttycontest.persistence.model.StaticFrequency;
import mx.fmre.rttycontest.persistence.model.VBandFrequency;
import mx.fmre.rttycontest.persistence.repository.ICatBandRepository;
import mx.fmre.rttycontest.persistence.repository.ICatFrequencyBandRepository;
import mx.fmre.rttycontest.persistence.repository.IStaticFrequencyRepository;
import mx.fmre.rttycontest.persistence.repository.IVBandFrequencyRepository;

@Service
@Slf4j
public class FrequencyBsServiceImpl implements IFrequencyBsService {
	
	@Autowired ICatFrequencyBandRepository        catFrequencyBandRepository;
	@Autowired private IVBandFrequencyRepository  vBandFrequencyRepository;
	@Autowired private ICatBandRepository         catBandRepository;
	@Autowired private IStaticFrequencyRepository staticFrequencyRepository;
	
	private List<CatBand> listBands;
	
	private static final BigDecimal cientoOchentaBigDecimal = BigDecimal.valueOf(180);
	private static final BigDecimal milBigDecimal = BigDecimal.valueOf(1000);
	
	@PostConstruct
	private void postConstruct() {
		this.listBands = catBandRepository.findAll();
	}

	@Override
	public CatBand getBandOfFrequency(BigDecimal frequency) {
	    
        BigDecimal freq = frequency.compareTo(cientoOchentaBigDecimal) == 1 ? frequency.divide(milBigDecimal) : frequency;
	    
		List<CatFrequencyBand> multipleFB = catFrequencyBandRepository.getByFrequency(freq);
		List<CatFrequencyBand> distinctBands = multipleFB
				.stream()
				.filter(CollectiosUtil.distinctByKey(CatFrequencyBand::getId))
				.collect(Collectors.toList());
		if(distinctBands.size() == 1) {
			Integer bandId = distinctBands.get(0).getBand().getId();
			CatBand band = listBands.stream().filter(b -> b.getId() == bandId).findFirst().orElse(null);
			return band;
		}
		if(distinctBands.size() > 1) {
			log.warn("Multiple bands for {} frequency", frequency);
		}
		
		//method 2
		//find in whole band
		List<VBandFrequency> bandFrequencies = vBandFrequencyRepository.findByFrequency(freq);
		if(bandFrequencies.size() == 1) {
			VBandFrequency bandFrequency =  bandFrequencies.get(0);
			Integer bandId = bandFrequency.getId();
			CatBand band = listBands.stream().filter(b -> b.getId() == bandId).findFirst().orElse(null);
			return band;
		}
		
		StaticFrequency staticFrequency = staticFrequencyRepository.findByFrequency(freq);
		if(staticFrequency != null) {
			Integer bandId = staticFrequency.getBand().getId();
			CatBand band = listBands.stream().filter(b -> b.getId() == bandId).findFirst().orElse(null);
			return band;
		}
		
		return null;
	}

}
