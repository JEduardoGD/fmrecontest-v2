package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.api.dto.QsoDto;
import mx.fmre.rttycontest.exception.FmreContestException;

public interface IQsoServie {

	public List<QsoDto> getQsosByConteoIdAndLogId(int conteoId, long logId) throws FmreContestException;

	public QsoDto findById(Integer conteoId, Long qsoId) throws FmreContestException;

	public QsoDto update(QsoDto qsoDto) throws FmreContestException;

	public boolean reevaluateLog(Long contestLogId, Integer conteoId, Integer editionId) throws FmreContestException;
}
