package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.dto.EmailDTO;
import mx.fmre.rttycontest.exception.FmreContestException;

public interface IEmailService {
	public List<EmailDTO> getAllByEdition(Integer editionid) throws FmreContestException;
	public List<EmailDTO> getAllBySubjectAndEditionID(String subject, Integer editionid) throws FmreContestException ;
}
