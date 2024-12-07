package mx.fmre.rttycontest.recibir.services.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.repository.IAttachedFileRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.business.IParserEmail;
import mx.fmre.rttycontest.recibir.services.IParserService;

@Service
public class ParserServiceImpl implements IParserService {

	@Autowired private IEditionRepository editionRepository;
	@Autowired private IEmailRepository emailRepository;
	@Autowired private IEmailEstatusRepository emailEstatusRepository;
	@Autowired private IParserEmail parserEmail;
	@Autowired private IAttachedFileRepository attachedFileRepository;
	@Autowired private IContestLogRepository contestLogRepository;

	@Value("${messages.perminute}")
	private Integer messagesPerminute;
	
	private EmailStatus emailEstatusRecived;
	private EmailStatus emailEstatusIdentified;
	private EmailStatus emailEstatusNoIdentified;
	private EmailStatus emailEstatusIgnored;
	private EmailStatus emailEstatusParsed;
	private EmailStatus emailEstatusNotParsed;

	@PostConstruct
	private void init() {
		emailEstatusRecived = emailEstatusRepository.findByStatus("RECIVED");
		emailEstatusIdentified = emailEstatusRepository.findByStatus("IDENTIFIED");
		emailEstatusNoIdentified = emailEstatusRepository.findByStatus("NO_IDENTIFIED");
		emailEstatusIgnored = emailEstatusRepository.findByStatus("IGNORED");
		emailEstatusParsed = emailEstatusRepository.findByStatus("PARSED");
		emailEstatusNotParsed = emailEstatusRepository.findByStatus("NO_PARSED");
	}

	@Override
	public void identifyLogFiles() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<Email> emails = emailRepository.findByEditionAndEmailStatusesAndNotVerified(edition,
					Arrays.asList(emailEstatusRecived));
			if (emails.size() > messagesPerminute)
				emails = emails.subList(0, messagesPerminute);
			for (Email email : emails) {
				if (this.doIgnore(email)) {
					email.setEmailStatus(emailEstatusIgnored);
					emailRepository.save(email);
					continue;
				}
				List<AttachedFile> listAttachedFiles = parserEmail.identifyLogFile(email);
				if (listAttachedFiles != null && listAttachedFiles.size() == 1) {
					AttachedFile logFile = listAttachedFiles.get(0);
					logFile.setLogFile(true);
					attachedFileRepository.save(logFile);
					email.setEmailStatus(emailEstatusIdentified);
					emailRepository.save(email);
				} else {
					email.setEmailStatus(emailEstatusNoIdentified);
					emailRepository.save(email);
				}
			}
		}
	}

	private boolean doIgnore(Email email) {
		String subject = email.getSubject();
		if (subject != null && (
				subject.toLowerCase().contains("undelivered") || 
				subject.toLowerCase().contains("spam") || 
				subject.toLowerCase().startsWith("mail delivery deferred")) ||
		        subject.toLowerCase().contains("mail delivery failed: returning message to sender") ||
		        subject.toLowerCase().startsWith("no se puede entregar"))
			return true;
		if(email.getRecipientsFromAddress().toLowerCase().contains("rtty@fmre.mx"))
			return true;
		return false;
	}

	@Override
	public void parseRecivedEmails() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<Email> emails = emailRepository.findByEditionAndEmailStatusesAndNotVerified(edition,
					Arrays.asList(emailEstatusIdentified));
			if (emails.size() > messagesPerminute)
				emails = emails.subList(0, messagesPerminute);
			for (Email email : emails) {
				ContestLog contestLog = parserEmail.parse(email);
				if (contestLog != null) {
					contestLogRepository.save(contestLog);
					email.setEmailStatus(emailEstatusParsed);
					emailRepository.save(email);
				} else {
					email.setEmailStatus(emailEstatusNotParsed);
					emailRepository.save(email);
				}
			}
		}
	}
}
