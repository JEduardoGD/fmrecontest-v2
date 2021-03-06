package mx.fmre.rttycontest.recibir.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.enums.EmailEstatusEnum;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.ScannerThread;
import mx.fmre.rttycontest.recibir.services.IFileManagerService;
import mx.fmre.rttycontest.recibir.services.IMailService;

@Service
public class MailServiceImpl implements IMailService {

	@Autowired
	private IEditionRepository editionRepository;

	@Autowired
	private IEmailRepository emailRepository;

	@Autowired
	private IContestRepository contestRepository;

	@Value("${email.password.encodingkey}")
	private String emailPasswordEncodingkey;

	@Value("${email.fields.to.legth}")
	private int emailFieldsToLenght;
	
	@Autowired
    private ApplicationContext appContext;
	
	@Value("${file.manager.impl}")
	private String fileManagerImpl;
	
	@Value("${messages.perminute}")
	private Integer messagesPerminute;
	
	@Autowired private IEmailEstatusRepository emailEstatusRepository;

	@Override
	public void scanContest() {
		IFileManagerService fileManagerService = appContext.getBean(fileManagerImpl, IFileManagerService.class);
		
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		EmailStatus emailEstatusRecived = emailEstatusRepository.findById(EmailEstatusEnum.RECIVED.getId()).orElse(null);
		editions.forEach(edition -> {
			ScannerThread st = new ScannerThread(
					edition, 
					contestRepository, 
					emailPasswordEncodingkey, 
					emailRepository,
					emailFieldsToLenght, 
					fileManagerService,
					emailEstatusRecived,
					messagesPerminute);
			st.run();
		});
	}
}