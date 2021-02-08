package mx.fmre.rttycontest.recibir.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.mail.internet.InternetAddress;

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
import mx.fmre.rttycontest.recibir.dto.OthersLogs;
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
	
	@Value("${messages.perminute}")
	private Integer messagesPerminute;
	
	@Autowired MailContentBuilder mailContentBuilder;
	
	List<EmailStatus> listEstatuses;

	@PostConstruct
	private void init() {
		EmailStatus emailEstatusNoIdentified = emailEstatusRepository.findByStatus("NO_IDENTIFIED");
		EmailStatus emailEstatusParsed = emailEstatusRepository.findByStatus("PARSED");
		EmailStatus emailEstatusNoParsed = emailEstatusRepository.findByStatus("NO_PARSED");
		listEstatuses = Arrays.asList(emailEstatusNoIdentified, emailEstatusParsed, emailEstatusNoParsed);
	}
	
	@Override
	public void responseParsedEmail() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			Integer idContest = edition.getContest().getId();
			Contest contest = contestRepository.findById(idContest).orElse(null);
			EmailAccount emailAccount = emailAccountRepository.findByContest(contest);
			List<Email> emails = emailRepository.findByEditionAndEmailStatusesAndVerifiedAndNotAnswered(edition, listEstatuses);
			if (emails.size() > messagesPerminute)
				emails = emails.subList(0, messagesPerminute);
			for (Email email : emails) {
				
				List<CatEmailError> errors = emailErrorRepository.getErrorsOfEmail(email);
				List<ErrorDTO> errorsDto = errors.stream()
						.map(x -> new ErrorDTO(x.getSuggestionEs(), x.getSuggestionEn()))
						.collect(Collectors.toList());
				
				EmailDataDTO emailDataDTO = new EmailDataDTO();
				emailDataDTO.setErrors(errorsDto);
				emailDataDTO.setOthersLogs(this.createOthersLogs(email, edition));

				//desde donde llego el correo actual
				emailDataDTO.setEmailSubject(email.getSubject());
				emailDataDTO.setFromName(email.getRecipientsFromName());
				emailDataDTO.setFromAddress(email.getRecipientsFromAddress());
				emailDataDTO.setTo(email.getRecipientsTo());

				//nuevo correo
				emailDataDTO.setSubject(contest.getDescription() + " " + edition.getDescription());
				emailDataDTO.setToName(email.getRecipientsFromName());
				
				if (edition.getTest()) {
					emailDataDTO.setToAddress(edition.getEmailTest());
					emailDataDTO.setBcc(null);
				} else {
					emailDataDTO.setToAddress(email.getRecipientsFromAddress());
					emailDataDTO.setBcc(edition.getBcc());
				}
				emailDataDTO.setTemplate(edition.getTemplate());
				emailDataDTO.setDateOfSend(email.getSentDate());
				emailDataDTO.setCallsign(email.getSubject());
				emailDataDTO.setDebugData(email.getId() + "");
				
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
					if(this.prepareAndSend(emailAccount, contest.getDescription(), emailDataDTO, jsm)) {
						email.setAnsweredAt(DateTimeUtil.getUtcTimeDate());
						emailRepository.save(email);
					}
					
				} catch (FmreContestException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private List<OthersLogs> createOthersLogs(Email email, Edition edition) {
		List<Email> listEmails = emailRepository.getAllBySubjectAndEditionBeforeDate(email.getSubject(), edition, email.getSentDate());
		return listEmails
				.stream()
				.filter(e -> !e.equals(email))
				.map(e -> {
			ContestLog contestLog = contestLogRepository.findByEmail(email);
			List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
			List<CatEmailError> listErrors = emailErrorRepository.getErrorsOfEmail(email);
			OthersLogs othersLogs = new OthersLogs();
			othersLogs.setFromName(e.getRecipientsFromName());
			othersLogs.setDateOfSend(e.getSentDate());
			othersLogs.setNoQsos(qsos.size());
			othersLogs.setHasErrors(listErrors.size() > 0);
			return othersLogs;
		}).collect(Collectors.toList());
	}
	
	private JavaMailSender getJsm(EmailAccount emailAccount) {
		EncryptDecryptStringHelper encryptDecryptStringHelper = null;
		try {
			encryptDecryptStringHelper = new EncryptDecryptStringHelper(emailPasswordEncodingkey);
		} catch (FmreContestException e) {
			e.printStackTrace();
		}
		
		if(encryptDecryptStringHelper == null) {
			return null;
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

	public boolean prepareAndSend(EmailAccount emailAccount, String newMailFromName, EmailDataDTO emailDataDTO, JavaMailSender mailSender) throws FmreContestException {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			
			List<ErrorDTO> errors = emailDataDTO.getErrors().stream().map(e -> {
				e.setSuggestionEs( changeHtmlCharaters(e.getSuggestionEs()));
				return e;
			}).collect(Collectors.toList());
			emailDataDTO.setErrors(errors);
			
			messageHelper.setFrom(emailAccount.getEmailAddress(), newMailFromName);
			messageHelper.setReplyTo(new InternetAddress(emailAccount.getReplyToEmail(), emailAccount.getReplyToName()));

			if (emailDataDTO.getToName() != null && !"".equals(emailDataDTO.getToName())) {
				messageHelper.setTo(new InternetAddress(emailDataDTO.getToAddress(), emailDataDTO.getToName()));
			} else {
				messageHelper.setTo(new InternetAddress(emailDataDTO.getToAddress()));
			}
			
			if (emailDataDTO.getBcc() != null) {
				messageHelper.setBcc(new InternetAddress(emailDataDTO.getBcc()));
			}

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
	
	private String changeHtmlCharaters(String text) {
		if (text == null) {
			return null;
		}
		if (text == "") {
			return "";
		}
		return text
				.replaceAll("\\Á", "&Aacute;")
				.replaceAll("\\É", "&Eacute;")
				.replaceAll("\\Í", "&Iacute;")
				.replaceAll("\\Ó", "&Oacute;")
				.replaceAll("\\Ú", "&Uacute;")
				.replaceAll("\\á", "&aacute;")
				.replaceAll("\\é", "&eacute;")
				.replaceAll("\\í", "&iacute;")
				.replaceAll("\\ó", "&oacute;")
				.replaceAll("\\ú", "&uacute;");
	}
}
