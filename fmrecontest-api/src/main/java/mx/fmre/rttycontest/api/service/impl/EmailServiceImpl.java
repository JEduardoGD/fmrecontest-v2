package mx.fmre.rttycontest.api.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.service.IEmailService;
import mx.fmre.rttycontest.api.util.AttachedFileUtil;
import mx.fmre.rttycontest.dto.EmailDTO;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.repository.IAttachedFileRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;

@Service
public class EmailServiceImpl implements IEmailService {
	
	@Autowired private IEmailRepository emailRepository;
	@Autowired private IEditionRepository editionRepository;
	@Autowired private EmailStatusService emailStatusService;
	@Autowired private IAttachedFileRepository attachedFileRepository;
	
	private Map<Integer, EmailStatus> mapStatuses = new HashMap<>();
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private AttachedFileUtil attachedFileUtil = new AttachedFileUtil();

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
			emailDTO.setId(e.getId());
			emailDTO.setRecipientsFromAddress(e.getRecipientsFromAddress());
			emailDTO.setRecipientsFromName(e.getRecipientsFromName());
			emailDTO.setEmailStatus(emailStatus.getStatus());
			
			List<AttachedFile> attachedFiles = attachedFileRepository.getByEmail(e);
			emailDTO.setAtachedFiles(attachedFileUtil.map(attachedFiles));
			return emailDTO;
		}).collect(Collectors.toList());
	}
}
