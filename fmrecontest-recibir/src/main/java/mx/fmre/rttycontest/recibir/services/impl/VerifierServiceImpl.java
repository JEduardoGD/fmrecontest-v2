package mx.fmre.rttycontest.recibir.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.util.DateTimeUtil;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailEmailError;
import mx.fmre.rttycontest.persistence.model.EmailEmailErrorPk;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.repository.EmailEmailErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.business.IVerificacionEmail;
import mx.fmre.rttycontest.recibir.services.IVerifierService;

@Service
public class VerifierServiceImpl implements IVerifierService {

	@Autowired private IEmailEstatusRepository emailEstatusRepository;
	@Autowired private IEditionRepository editionRepository;
	@Autowired private IEmailRepository emailRepository;
	@Autowired private IVerificacionEmail verificacionEmail;
	@Autowired private EmailEmailErrorRepository emailEmailErrorRepository;

	@Override
	public void verifyRecivedEmails() {
		EmailStatus emailEstatusNoIdentified = emailEstatusRepository.findByStatus("NO_IDENTIFIED");
		EmailStatus emailEstatusParsed = emailEstatusRepository.findByStatus("PARSED");
		EmailStatus emailEstatusNoParsed = emailEstatusRepository.findByStatus("NO_PARSED");
		List<EmailStatus> listEstatuses = Arrays.asList(emailEstatusNoIdentified, emailEstatusParsed, emailEstatusNoParsed);
		
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<Email> emails = emailRepository.findByEditionAndEmailStatusesAndNotVerified(edition, listEstatuses);
			for (Email email : emails) {
				try {
					List<CatEmailError> result = verificacionEmail.verify(email);
					List<EmailEmailError> listErrors = result.stream().map(r -> {
						EmailEmailError emailEmailError = new EmailEmailError();
						EmailEmailErrorPk pk = new EmailEmailErrorPk();
						pk.setCatErrorid(r.getId());
						pk.setEmailId(email.getId());
						emailEmailError.setPk(pk);
						emailEmailError.setEmail(email);
						emailEmailError.setEmailError(r);
						emailEmailError.setFechaRegistro(DateTimeUtil.getUtcTimeDate());
						return emailEmailError;
					}).collect(Collectors.toList());
					emailEmailErrorRepository.saveAll(listErrors);
					email.setVerifiedAt(DateTimeUtil.getUtcTimeDate());
					emailRepository.save(email);
				} catch (FmreContestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
