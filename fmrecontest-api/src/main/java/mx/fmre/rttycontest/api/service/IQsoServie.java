package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.api.dto.QsoDto;
import mx.fmre.rttycontest.exception.FmreContestException;

public interface IQsoServie {

	List<QsoDto> getQsosByConteoIdAndLogId(int conteoId, long logId) throws FmreContestException;

}
