package mx.fmre.rttycontest.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.service.IEmailStatusService;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;

@Service
public class EmailStatusService implements IEmailStatusService {
	
	@Autowired
	IEmailEstatusRepository emailEstatusRepository;

	@Override
	public EmailStatus getById(Integer emailStatusId) {
		return emailEstatusRepository.findById(emailStatusId).get();
	}
}
