package mx.fmre.rttycontest.recibir.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.repository.ICatEmailErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.services.IResponderService;
import mx.fmre.rttycontest.recibir.services.MailClient;

@Service
public class ResponderServiceImpl implements IResponderService {

	@Autowired private IEmailEstatusRepository emailEstatusRepository;
	@Autowired private IEditionRepository editionRepository;
	@Autowired private IEmailRepository emailRepository;
	@Autowired private ICatEmailErrorRepository emailErrorRepository;
	@Autowired private MailClient mailClient;

	@Override
	public void responseParsedEmail() {
		String recipient = "eduardo_gd@hotmail.com";
        String message = "Test message content";
        mailClient.prepareAndSend(recipient, message);

//		EmailStatus emailEstatusNoIdentified = emailEstatusRepository.findByStatus("NO_IDENTIFIED");
//		EmailStatus emailEstatusParsed = emailEstatusRepository.findByStatus("PARSED");
//		EmailStatus emailEstatusNoParsed = emailEstatusRepository.findByStatus("NO_PARSED");
//		List<EmailStatus> listEstatuses = Arrays.asList(emailEstatusNoIdentified, emailEstatusParsed, emailEstatusNoParsed);
//		
//		List<Edition> editions = editionRepository.getActiveEditionOfContest();
//		for (Edition edition : editions) {
//			List<Email> emails = emailRepository.findByEditionAndEmailStatusesAndVerifiedAndNotAnswered(edition, listEstatuses);
//			for (Email email : emails) {
//				@SuppressWarnings("unused")
//				List<CatEmailError> errors = emailErrorRepository.getErrorsOfEmail(email);
//				if(errors.isEmpty()) {
//					
//				} else {
//					
//				}
//			}
//		}
	}
}
