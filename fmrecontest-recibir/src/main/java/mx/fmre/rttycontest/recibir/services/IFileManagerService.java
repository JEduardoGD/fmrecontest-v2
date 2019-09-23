package mx.fmre.rttycontest.recibir.services;

import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.recibir.dto.AttachedFileDTO;

public interface IFileManagerService {

	public String saveFile(Email email, AttachedFileDTO fileDTO);

	public void saveFile(AttachedFileDTO fileDTO);

}
