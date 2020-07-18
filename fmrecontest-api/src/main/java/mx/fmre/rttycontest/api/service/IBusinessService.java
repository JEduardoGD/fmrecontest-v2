package mx.fmre.rttycontest.api.service;

import mx.fmre.rttycontest.api.dto.ContestlogDto;
import mx.fmre.rttycontest.exception.FmreContestException;

public interface IBusinessService {
	public ContestlogDto recalculateLog(ContestlogDto contestlogDto) throws FmreContestException;
}
