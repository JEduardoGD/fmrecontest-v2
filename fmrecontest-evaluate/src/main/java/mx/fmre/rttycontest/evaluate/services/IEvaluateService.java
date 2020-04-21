package mx.fmre.rttycontest.evaluate.services;

import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;

public interface IEvaluateService {
	public Conteo createConteo(Edition edition, String description);
	public void findForErrorsOnQsos(Conteo conteo, Edition edition);
	public void setPointsForQssos(Conteo conteo);
	public void setMultipliesQsos(Conteo conteo);
	public void evaluateActiveEditions(Conteo conteo);
	public void findForErrorsOnQsosOfLog(ContestLog contestLog, Conteo conteo);
}
