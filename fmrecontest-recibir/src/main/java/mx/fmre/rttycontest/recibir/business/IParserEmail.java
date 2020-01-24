package mx.fmre.rttycontest.recibir.business;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Email;

public interface IParserEmail {
	public List<AttachedFile> identifyLogFile(Email email);
	public ContestLog parse(Email email);
}
