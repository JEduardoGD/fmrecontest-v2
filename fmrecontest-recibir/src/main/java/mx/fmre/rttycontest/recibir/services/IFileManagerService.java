package mx.fmre.rttycontest.recibir.services;

import mx.fmre.rttycontest.bs.dto.AttachedFileDTO;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.Email;

public interface IFileManagerService {

	public String saveFile(Email email, AttachedFileDTO fileDTO) throws FmreContestException;

	public AttachedFileDTO getFile(Email email, AttachedFileDTO fileDTO) throws FmreContestException;
}
