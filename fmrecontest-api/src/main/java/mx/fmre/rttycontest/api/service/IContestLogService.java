package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.api.dto.ContestlogDto;
import mx.fmre.rttycontest.api.dto.QsoDto;
import mx.fmre.rttycontest.exception.FmreContestException;

public interface IContestLogService {

	public List<ContestlogDto> findByConteoId(Integer conteoId) throws FmreContestException;

	public List<QsoDto> getContestlogByConteoIdAndLogId(int conteoId, long logId) throws FmreContestException;

}
