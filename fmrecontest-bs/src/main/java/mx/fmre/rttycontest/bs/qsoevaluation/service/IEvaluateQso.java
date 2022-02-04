package mx.fmre.rttycontest.bs.qsoevaluation.service;

import java.util.List;

import mx.fmre.rttycontest.bs.gridlocator.service.impl.LocatorServiceException;
import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;

public interface IEvaluateQso {
	public List<CatQsoError> findForErrors(Edition edition, ContestLog contestLog, ContestQso qso, List<CatQsoError> qsoErrors);
	public Integer getPoints(ContestLog contestLog, ContestQso qso) throws LocatorServiceException;
	public void setMultiplies(Conteo conteo, List<ContestQso> qsos);
}
