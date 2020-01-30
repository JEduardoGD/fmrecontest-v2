package mx.fmre.rttycontest.recibir.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.util.DateTimeUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailAccount;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.repository.ICatEmailErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailAccountRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.dto.EmailDataDTO;
import mx.fmre.rttycontest.recibir.dto.ErrorDTO;
import mx.fmre.rttycontest.recibir.helper.EncryptDecryptStringHelper;
import mx.fmre.rttycontest.recibir.services.IResponderService;
import mx.fmre.rttycontest.recibir.services.MailContentBuilder;

@Service
public class ResponderServiceImpl implements IResponderService {

	@Autowired private IEmailEstatusRepository emailEstatusRepository;
	@Autowired private IEditionRepository editionRepository;
	@Autowired private IEmailRepository emailRepository;
	@Autowired private ICatEmailErrorRepository emailErrorRepository;
	@Autowired private IContestRepository contestRepository;
	@Autowired private IEmailAccountRepository emailAccountRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IContestQsoRepository contestQsoRepository;
	
	@Value("${email.password.encodingkey}")
	private String emailPasswordEncodingkey;
	
	@Value("${email.responder.testmode}")
	private boolean emailResponderTestmode;
	
	@Value("${email.responder.testemail}")
	private String emailResponderTestmail;
	
	@Value("${email.responder.co}")
	private String emailResponderCopiaoculta;
	
	@Autowired MailContentBuilder mailContentBuilder;
	
	@Override
	public void responseParsedEmail() {
		EmailStatus emailEstatusNoIdentified = emailEstatusRepository.findByStatus("NO_IDENTIFIED");
		EmailStatus emailEstatusParsed = emailEstatusRepository.findByStatus("PARSED");
		EmailStatus emailEstatusNoParsed = emailEstatusRepository.findByStatus("NO_PARSED");
		
		List<EmailStatus> listEstatuses = Arrays.asList(
				emailEstatusNoIdentified, 
				emailEstatusParsed, 
				emailEstatusNoParsed);
		
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			Integer idContest = edition.getContest().getId();
			Contest contest = contestRepository.findById(idContest).orElse(null);
			EmailAccount emailAccount = emailAccountRepository.findByContest(contest);
			List<Email> emails = emailRepository.findByEditionAndEmailStatusesAndVerifiedAndNotAnswered(edition, listEstatuses);
			for (Email email : emails) {
				EmailDataDTO emailDataDTO = new EmailDataDTO();
				
				List<CatEmailError> errors = emailErrorRepository.getErrorsOfEmail(email);
				List<ErrorDTO> errorsDto = errors.stream()
						.map(x -> new ErrorDTO(x.getSuggestionEs(), x.getSuggestionEn()))
						.collect(Collectors.toList());
				emailDataDTO.setErrors(errorsDto);
				
				emailDataDTO.setRecipientFrom(email.getRecipientsFromAddress());
				emailDataDTO.setRecipientTo(email.getRecipientsTo());
				emailDataDTO.setTemplate(edition.getTemplate());
				emailDataDTO.setDateOfSend(email.getSentDate());
				emailDataDTO.setCallsign(email.getSubject());
				
				ContestLog contestLog = contestLogRepository.findByEmail(email);
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
				emailDataDTO.setNoQsos(qsos.size());
				
				if(errors.isEmpty()) {
					emailDataDTO.setSubject(emailAccount.getSuccessfullyMsg());
				} else {
					emailDataDTO.setSubject(emailAccount.getErrorMsg());	
				}
				
				JavaMailSender jsm = this.getJsm(emailAccount);
				try {
					if(this.prepareAndSend(emailDataDTO, jsm)) {
						email.setAnsweredAt(DateTimeUtil.getUtcTimeDate());
						emailRepository.save(email);
					}
					
				} catch (FmreContestException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private JavaMailSender getJsm(EmailAccount emailAccount) {
		EncryptDecryptStringHelper encryptDecryptStringHelper = null;
		try {
			encryptDecryptStringHelper = new EncryptDecryptStringHelper(emailPasswordEncodingkey);
		} catch (FmreContestException e) {
			e.printStackTrace();
		}

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(emailAccount.getOutHost());
		mailSender.setPort(emailAccount.getOutPort());

		mailSender.setUsername(emailAccount.getUsername());
		try {
			mailSender.setPassword(encryptDecryptStringHelper.decrypt(emailAccount.getPassword()));
		} catch (FmreContestException e) {
			e.printStackTrace();
		}

		Properties props = mailSender.getJavaMailProperties();
		String smtpProperties = emailAccount.getSmtpProperties();
		List<String> listProperties = Arrays.asList(smtpProperties.split("\\;"));
		for (String s : listProperties) {
			String[] arr = s.split("\\:");
			props.put(arr[0], arr[1]);
		}

		JavaMailSender jsm = (JavaMailSender) mailSender;
		return jsm;
	}

	public boolean prepareAndSend(EmailDataDTO emailDataDTO, JavaMailSender mailSender) throws FmreContestException {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(emailDataDTO.getRecipientTo());
			if (emailResponderTestmode)
				messageHelper.setTo(emailResponderTestmail);
			else
				messageHelper.setTo(emailDataDTO.getRecipientTo());
			if (emailResponderCopiaoculta != null && !"".equals(emailResponderCopiaoculta))
				messageHelper.setBcc(emailResponderCopiaoculta);
			messageHelper.setSubject(emailDataDTO.getSubject());
			String content = mailContentBuilder.build(emailDataDTO);
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			throw new FmreContestException(e.getLocalizedMessage());
		}
		return true;
	}
}
