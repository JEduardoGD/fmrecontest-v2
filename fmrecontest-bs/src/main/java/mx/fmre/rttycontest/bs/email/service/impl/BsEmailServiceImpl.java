package mx.fmre.rttycontest.bs.email.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.email.service.IBsEmailService;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.persistence.repository.ILastEmailRepository;

@Service
public class BsEmailServiceImpl implements IBsEmailService {
    @Autowired private ILastEmailRepository lastEmailRepository;
    @Autowired private IEditionRepository editionRepository;
    @Autowired private IEmailRepository emailRepository;
    
    @Override
    public List<Email> getValidEmailsOfEdition(int editionId) {

        List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(editionId);
        Edition edition = editionRepository.findById(editionId).orElse(null);
        List<Email> emails = emailRepository.findByEdition(edition);

        List<Integer> lastEmailsIdList = lastEmails.stream().map(LastEmail::getEmailId).collect(Collectors.toList());

        return emails.stream().filter(e -> lastEmailsIdList.contains(e.getId()))
                .collect(Collectors.toList());
    }
}
