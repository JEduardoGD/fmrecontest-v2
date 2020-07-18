package mx.fmre.rttycontest.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.dto.ContestlogDto;
import mx.fmre.rttycontest.api.service.IBusinessService;
import mx.fmre.rttycontest.evaluate.services.IEvaluateLogService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;

@Service
public class BusinessServiceImpl implements IBusinessService {
	
	@Autowired private IEvaluateLogService evaluateLogService;
	@Autowired private IContestLogRepository contestLogRepository;

	@Override
	public ContestlogDto recalculateLog(ContestlogDto contestlogDto) throws FmreContestException {
		ContestLog contestLog = contestLogRepository.findById(contestlogDto.getId()).orElse(null);
		evaluateLogService.evaluateLog(contestLog.getId(), contestlogDto.getConteoId());
		return null;
	}

}
