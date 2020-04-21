package mx.fmre.rttycontest.bs.frequency.service;

import java.math.BigDecimal;

import mx.fmre.rttycontest.persistence.model.CatBand;

public interface IFrequencyBsService {
	public CatBand getFrequencyBandOf(BigDecimal frequency);
}
