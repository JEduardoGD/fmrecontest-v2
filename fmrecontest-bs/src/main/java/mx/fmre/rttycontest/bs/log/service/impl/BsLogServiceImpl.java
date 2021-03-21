package mx.fmre.rttycontest.bs.log.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.email.service.IBsEmailService;
import mx.fmre.rttycontest.bs.log.service.IBsLogService;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;

@Service
public class BsLogServiceImpl implements IBsLogService {
    @Autowired private IContestLogRepository contestLogRepository;
    @Autowired private IBsEmailService emailService;
    
    @Override
    public List<ContestLog> getValidLogsOfEdition(int editionId) {
        List<Email> emails = emailService.getValidEmailsOfEdition(editionId);

        return emails.stream().map(email -> contestLogRepository.findByEmail(email))
                .collect(Collectors.toList());
    }
}
