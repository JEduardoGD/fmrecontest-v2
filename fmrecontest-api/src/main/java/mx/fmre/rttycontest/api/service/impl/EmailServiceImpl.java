package mx.fmre.rttycontest.api.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.service.IEmailService;
import mx.fmre.rttycontest.api.util.AttachedFileUtil;
import mx.fmre.rttycontest.bs.util.CollectiosUtil;
import mx.fmre.rttycontest.dto.EmailDTO;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.CatFrequencyBand;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.repository.IAttachedFileRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.persistence.repository.ILastEmailRepository;

@Service
public class EmailServiceImpl implements IEmailService {
	
	@Autowired private IEmailRepository emailRepository;
	@Autowired private IEditionRepository editionRepository;
	@Autowired private EmailStatusService emailStatusService;
	@Autowired private IAttachedFileRepository attachedFileRepository;
	@Autowired private ILastEmailRepository lastEmailRepository;
	
	private Map<Integer, EmailStatus> mapStatuses = new HashMap<>();
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<EmailDTO> getAllByEdition(Integer editionid) throws FmreContestException {
		Optional<Edition> editionOptional = editionRepository.findById(editionid);
		
		if(!editionOptional.isPresent())
			throw new FmreContestException("Edition not found");

		return emailRepository.findByEdition(editionOptional.get())
		.stream()
		.map(e -> {
			EmailStatus emailStatus = mapStatuses.get(e.getEmailStatus().getId());
			emailStatus = emailStatus == null ? emailStatusService.getById(e.getEmailStatus().getId()) : emailStatus;
			mapStatuses.put(emailStatus.getId(), emailStatus);
			
			EmailDTO emailDTO = new EmailDTO();
			emailDTO.setSubject(e.getSubject());
			emailDTO.setSentDate(df.format(e.getSentDate()));
			emailDTO.setReceivedDate(df.format(e.getReceivedDate()));
			emailDTO.setId(e.getId());
			emailDTO.setRecipientsFromAddress(e.getRecipientsFromAddress());
			emailDTO.setRecipientsFromName(e.getRecipientsFromName());
			emailDTO.setEmailStatus(emailStatus.getStatus());
			
			List<AttachedFile> attachedFiles = attachedFileRepository.findByEmail(e);
			emailDTO.setAtachedFiles(AttachedFileUtil.map(attachedFiles));
			return emailDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public List<EmailDTO> getAllBySubjectAndEditionID(String subject, Integer editionid) throws FmreContestException {
		Optional<Edition> editionOptional = editionRepository.findById(editionid);
		
		if(!editionOptional.isPresent())
			throw new FmreContestException("Edition not found");

		return emailRepository.findByEditionAndSubject(editionOptional.get(), subject)
		.stream()
		.map(e -> {
			EmailStatus emailStatus = mapStatuses.get(e.getEmailStatus().getId());
			emailStatus = emailStatus == null ? emailStatusService.getById(e.getEmailStatus().getId()) : emailStatus;
			mapStatuses.put(emailStatus.getId(), emailStatus);
			
			EmailDTO emailDTO = new EmailDTO();
			emailDTO.setSubject(e.getSubject());
			emailDTO.setSentDate(df.format(e.getSentDate()));
			emailDTO.setReceivedDate(df.format(e.getReceivedDate()));
			emailDTO.setId(e.getId());
			emailDTO.setRecipientsFromAddress(e.getRecipientsFromAddress());
			emailDTO.setRecipientsFromName(e.getRecipientsFromName());
			emailDTO.setEmailStatus(emailStatus.getStatus());
			
			List<AttachedFile> attachedFiles = attachedFileRepository.findByEmail(e);
			emailDTO.setAtachedFiles(AttachedFileUtil.map(attachedFiles));
			return emailDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public List<EmailDTO> getOnlyErrores(Integer editionId) throws FmreContestException {
		Edition edition = editionRepository.findById(editionId).orElse(null);
		
		List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(editionId);
		List<String> lastSubjects = lastEmails.stream().map(LastEmail::getEmailSubject).collect(Collectors.toList());
		List<Integer> lastEmailIds = lastEmails.stream().map(le -> le.getEmailId()).collect(Collectors.toList());
		
		List<Long> lastLogsIdsList = lastEmails
				.stream()
				.map(email -> new Long(email.getContestLogId()))
				.collect(Collectors.toList());
		
		List<Email> allEmails = emailRepository.findByEdition(edition);
		List<Email> notUsedEmails = allEmails.stream().filter(e -> !lastSubjects.contains(e.getSubject())).collect(Collectors.toList());
		
		List<String> disctinctSubjects = allEmails.stream()
				.filter(CollectiosUtil.distinctByKey(Email::getSubject))
				.map(e -> e.getSubject())
				.collect(Collectors.toList());
		

//		List<CatFrequencyBand> distinctBands = multipleFB
//				.stream()
//				.filter(CollectiosUtil.distinctByKey(CatFrequencyBand::getId))
//				.collect(Collectors.toList());
		return null;
	}
}
