package mx.fmre.rttycontest.bs.validation;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.ContestQso;

public interface IQsoValidation {
	public List<CatQsoError> validate(ContestQso contestQso);
}
