package mx.fmre.rttycontest.recibir.services;

import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.recibir.dto.AttachedFileDTO;

public interface IFileManagerService {

	public String saveFile(Email email, AttachedFileDTO fileDTO) throws FmreContestException;

	public void saveFile(AttachedFileDTO fileDTO) throws FmreContestException;

}
