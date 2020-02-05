package mx.fmre.rttycontest.evaluate.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.evaluate.services.ICompleteDxccService;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;

@Service
public class CompleteDxccServiceImpl implements ICompleteDxccService {

	@Autowired
	IEditionRepository editionRepository;
	@Autowired
	IEmailRepository emailRepository;
	@Autowired
	IContestLogRepository contestLogRepository;
	@Autowired
	IContestQsoRepository contestQsoRepository;

	@Value("${messages.perminute}")
	private Integer messagesPerminute;

	@Override
	public void complete() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<Email> emails = emailRepository.getAllWithLogfileByEdition(edition);
			if (emails.size() > messagesPerminute)
				emails = emails.subList(0, messagesPerminute);

			for (Email email : emails) {
				ContestLog contestLog = contestLogRepository.findByEmail(email);
				List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
				
			}
		}
	}
}
