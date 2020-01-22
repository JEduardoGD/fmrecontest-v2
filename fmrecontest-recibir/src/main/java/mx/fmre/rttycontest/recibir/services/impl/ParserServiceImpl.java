package mx.fmre.rttycontest.recibir.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.services.IParserService;

@Service
public class ParserServiceImpl implements IParserService {

	@Autowired
	private IEditionRepository editionRepository;

	@Autowired
	private IEmailRepository emailRepository;

	@Override
	public void parse() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			
		}
	}

}
