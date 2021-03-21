package mx.fmre.rttycontest.bs.reports.service;

import mx.fmre.rttycontest.exception.FmreContestException;

public interface ICsvReportsService {
	public byte[] generateConteoReport(int conteoId);
	public byte[] getAllEmailsOnByEditionId(int editionId);
	public byte[] generateLogReport(int conteoId, long logId);
	public byte[] getCallsignWithoutDxccEntityReport(int editionId);
    byte[] getCallersByEntity(int editionId) throws FmreContestException;
    byte[] getCalledByEntity(int editionId) throws FmreContestException;
    byte[] getEmailsWithErrors(int editionId) throws FmreContestException;
}
