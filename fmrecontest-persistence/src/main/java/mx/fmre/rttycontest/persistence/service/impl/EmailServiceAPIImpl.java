package mx.fmre.rttycontest.persistence.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.persistence.service.IEmailServiceAPI;

@Service
public class EmailServiceAPIImpl implements IEmailServiceAPI {
	
	@Autowired IEditionRepository editionRepository;
	@Autowired IEmailRepository emailRepository;

	@Override
	public List<Email> getByEditionId(int editionId) {
		Edition edition = editionRepository.findById(editionId).orElse(null);
		return emailRepository.findByEdition(edition);
	}

}
