package mx.fmre.rttycontest.bs.reports.service;

public interface ICsvReportsService {
	public byte[] generateRepor(int conteoId);

	public byte[] getAllEmailsOnByEditionId(int editionId);
}
