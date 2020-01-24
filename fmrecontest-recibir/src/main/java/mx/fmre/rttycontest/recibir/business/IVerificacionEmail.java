package mx.fmre.rttycontest.recibir.business;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.Email;

public interface IVerificacionEmail {
	public List<CatEmailError> verify(Email email);
}
