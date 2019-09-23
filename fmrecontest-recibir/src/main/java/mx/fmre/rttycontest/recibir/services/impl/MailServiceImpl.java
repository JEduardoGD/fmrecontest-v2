package mx.fmre.rttycontest.recibir.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.ScannerThread;
import mx.fmre.rttycontest.recibir.services.IFileManagerService;
import mx.fmre.rttycontest.recibir.services.IMailService;

@Service
public class MailServiceImpl implements IMailService {

	@Autowired
	private IEditionRepository editionRepository;

	@Autowired
	private IFileManagerService fileManagerService;

	@Autowired
	private IEmailRepository emailRepository;

	@Autowired
	private IContestRepository contestRepository;

	@Value("${email.password.encodingkey}")
	private String emailPasswordEncodingkey;

	@Value("${email.fields.to.legth}")
	private int emailFieldsToLenght;

	@Override
	public void scanContest() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		editions.forEach(edition -> {
			ScannerThread st = new ScannerThread(
					edition, 
					contestRepository, 
					emailPasswordEncodingkey, 
					emailRepository,
					emailFieldsToLenght, 
					fileManagerService);
			new Thread(st, edition.getDescription()).run();
		});
	}
}