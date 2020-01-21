package mx.fmre.rttycontest.api.service;

import mx.fmre.rttycontest.persistence.model.EmailStatus;

public interface IEmailStatusService {
	public EmailStatus getById(Integer id);
}
