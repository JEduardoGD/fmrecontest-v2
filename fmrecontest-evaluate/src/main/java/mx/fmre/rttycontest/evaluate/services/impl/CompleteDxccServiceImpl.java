package mx.fmre.rttycontest.evaluate.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.evaluate.services.ICompleteDxccService;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;

@Service
public class CompleteDxccServiceImpl implements ICompleteDxccService {
	
	@Autowired IEditionRepository editionRepository;
	@Autowired IEmailRepository emailRepository;

	@Override
	public void complete() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			@SuppressWarnings("unused")
			List<Email> emails = emailRepository.getAllWithLogfileByEdition(edition);
			
			System.out.println();
		}
	}
}
