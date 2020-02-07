package mx.fmre.rttycontest.evaluate.services;

public interface ICompleteDxccService {
	public void completeDxccEntityQsos();
	public void completeDxccEntityLogs();
	public void completeBandsOnQsos();
	public void findForErrorsOnQsos();
	public void setPointsForQssos();
	public void setMultipliesQsos();
	public void evaluateActiveEditions();
}
