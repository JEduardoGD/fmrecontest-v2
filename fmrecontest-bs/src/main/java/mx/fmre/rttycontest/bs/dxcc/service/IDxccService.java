package mx.fmre.rttycontest.bs.dxcc.service;

import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.DxccEntity;

public interface IDxccService {
	public DxccEntity getDxccFromCallsign(String callsign) throws FmreContestException;
}
