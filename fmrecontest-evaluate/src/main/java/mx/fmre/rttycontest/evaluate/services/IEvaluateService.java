package mx.fmre.rttycontest.evaluate.services;

import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;

public interface IEvaluateService {
	public Conteo createConteo(Edition edition, String description);
	public void findForErrorsOnQsos(Conteo conteo, Edition edition);
	public void setMultipliesQsos(Conteo conteo);
	public void evaluateActiveEditions(Conteo conteo);
    void setPointsForQssos(DxccEntity mexicoDxccEntity, Conteo conteo);
    void findForErrorsOnQsosOfLog(DxccEntity mexicoDxccEntity, ContestLog contestLog, Conteo conteo);
}
