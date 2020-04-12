package mx.fmre.rttycontest.evaluate.services;

public interface IEvaluateService {
	public void findForErrorsOnQsos();
	public void findForErrorsOnQsos(Long contestLogId, Integer conteoId, Integer editionId);
	public void setPointsForQssos();
	public void setMultipliesQsos();
	public void evaluateActiveEditions();
}
