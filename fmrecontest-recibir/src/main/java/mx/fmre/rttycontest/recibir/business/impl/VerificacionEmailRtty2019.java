package mx.fmre.rttycontest.recibir.business.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.recibir.business.IVerificacionEmail;

@Service("verificacionEmailRtty2019")
public class VerificacionEmailRtty2019 implements IVerificacionEmail {

	@Override
	public List<CatEmailError> verify(Email email) {
		// TODO Auto-generated method stub
		return null;
	}

}
