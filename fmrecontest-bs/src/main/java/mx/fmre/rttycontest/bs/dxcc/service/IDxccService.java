package mx.fmre.rttycontest.bs.dxcc.service;

import mx.fmre.rttycontest.bs.dxcc.dao.CallsignDAO;
import mx.fmre.rttycontest.exception.FmreContestException;

public interface IDxccService {
	public CallsignDAO getDxccFromCallsign(String callsign) throws FmreContestException;
}
