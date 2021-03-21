package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.dto.EmailDTO;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.Email;

public interface IEmailService {
	public List<EmailDTO> getAllByEdition(Integer editionid) throws FmreContestException;
	public List<EmailDTO> getAllBySubjectAndEditionID(String subject, Integer editionid) throws FmreContestException ;
	public List<EmailDTO> getOnlyErrores(Integer editionId) throws FmreContestException;
    List<Email> getEmailsWithErrores(Integer editionId) throws FmreContestException;
}
