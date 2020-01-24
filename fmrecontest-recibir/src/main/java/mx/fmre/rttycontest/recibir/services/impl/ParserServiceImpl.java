package mx.fmre.rttycontest.recibir.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.services.IParserService;

@Service
public class ParserServiceImpl implements IParserService {

	@Autowired
	private IEditionRepository editionRepository;

	@Autowired
	private IEmailRepository emailRepository;
	
	@Autowired
	private IEmailEstatusRepository emailEstatusRepository;

	@Override
	public void verifyRecivedEmails() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		EmailStatus emailEstatusRecived = emailEstatusRepository.findByStatus("RECIVED");
//		RECIVED
		for (Edition edition : editions) {
			List<Email> emails = emailRepository.findByEditionAndEmailStatus(edition, emailEstatusRecived);
			System.out.println();
		}
	}

}
