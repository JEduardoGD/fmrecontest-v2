package mx.fmre.rttycontest.bs.frequency.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.frequency.service.IFrequencyService;
import mx.fmre.rttycontest.bs.util.CollectiosUtil;
import mx.fmre.rttycontest.persistence.model.CatFrequencyBand;
import mx.fmre.rttycontest.persistence.repository.ICatFrequencyBandRepository;

@Service
@Slf4j
public class FrequencyServiceImpl implements IFrequencyService {
	
	@Autowired ICatFrequencyBandRepository catFrequencyBandRepository;

	@Override
	public CatFrequencyBand getFrequencyBandOf(BigDecimal frequency) {
		List<CatFrequencyBand> multipleFB = catFrequencyBandRepository.getByFrequency(frequency);
		List<CatFrequencyBand> distinctBands = multipleFB
				.stream()
				.filter(CollectiosUtil.distinctByKey(CatFrequencyBand::getId))
				.collect(Collectors.toList());
		if(distinctBands.size() == 1) {
			return distinctBands.get(0);
		}
		if(distinctBands.size() > 1) {
			log.warn("Multiple bands for {} frequency", frequency);
		}
		return null;
	}

}
