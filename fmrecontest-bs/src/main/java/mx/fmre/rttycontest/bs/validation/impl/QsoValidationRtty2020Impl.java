package mx.fmre.rttycontest.bs.validation.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.validation.IQsoValidation;
import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.ContestQso;

@Service("qsoValidationRtty2020")
public class QsoValidationRtty2020Impl implements IQsoValidation {

	@Override
	public List<CatQsoError> validate(ContestQso contestQso) {
		return null;
	}

}
