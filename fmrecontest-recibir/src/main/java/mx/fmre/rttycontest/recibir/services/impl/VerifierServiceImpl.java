package mx.fmre.rttycontest.recibir.services.impl;

import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.recibir.services.IVerifierService;

@Service
public class VerifierServiceImpl implements IVerifierService {

//	@Autowired private IEditionRepository editionRepository;
//	@Autowired private IEmailRepository emailRepository;
//	@Autowired private IEmailEstatusRepository emailEstatusRepository;
//	@Autowired private IVerificacionEmail verificacionEmail;

	@Override
	public void verifyRecivedEmails() {
//		EmailStatus emailEstatusNoIdentified = emailEstatusRepository.findByStatus("NO_IDENTIFIED");
//		EmailStatus emailEstatusParsed = emailEstatusRepository.findByStatus("PARSED");
//		EmailStatus emailEstatusNoParsed = emailEstatusRepository.findByStatus("NO_PARSED");
		
//		List<Edition> editions = editionRepository.getActiveEditionOfContest();
//		for (Edition edition : editions) {
//			List<Email> emails = emailRepository.findByEditionAndEmailStatuses(edition, 
//					Arrays.asList(emailEstatusNoIdentified, emailEstatusParsed, emailEstatusNoParsed));
//			for (Email email : emails) {
//				try {
//					@SuppressWarnings("unused")
//					List<CatEmailError> result = verificacionEmail.verify(email);
//				} catch (FmreContestException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
	}
}
