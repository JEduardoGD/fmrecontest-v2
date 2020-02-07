package mx.fmre.rttycontest.bs.frequency.service;

import java.math.BigDecimal;

import mx.fmre.rttycontest.persistence.model.CatFrequencyBand;

public interface IFrequencyService {
	public CatFrequencyBand getFrequencyBandOf(BigDecimal frequency);
}
