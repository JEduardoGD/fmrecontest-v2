package mx.fmre.rttycontest.bs.reports.service;

public interface IResultsReports {
	public byte[] highPowerWorld(int editionId);
	public byte[] lowPowerWorld(int editionId);
	public byte[] highPowerMexico(int conteoId);
	public byte[] lowPowerMexico(int conteoId);
	public byte[] lowPowerByCountry(int conteoId);
	public byte[] highPowerByCountry(int conteoId);
}
