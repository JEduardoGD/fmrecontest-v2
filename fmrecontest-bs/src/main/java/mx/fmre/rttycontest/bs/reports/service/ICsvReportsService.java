package mx.fmre.rttycontest.bs.reports.service;

public interface ICsvReportsService {
	public byte[] generateConteoReport(int conteoId);
	public byte[] getAllEmailsOnByEditionId(int editionId);
	public byte[] generateLogReport(int conteoId, long logId);
	public byte[] getCallsignWithoutDxccEntityReport(int editionId);
}
