package mx.fmre.rttycontest.persistence.service;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.Email;

public interface IEmailServiceAPI {
	public List<Email> getByEditionId(int editionId);
}
