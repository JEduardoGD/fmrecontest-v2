package mx.fmre.rttycontest.bs.reports.service;

import mx.fmre.rttycontest.persistence.model.Edition;

public interface ICsvReportsService {
	public byte[] generateRepor(Edition edition);
}
