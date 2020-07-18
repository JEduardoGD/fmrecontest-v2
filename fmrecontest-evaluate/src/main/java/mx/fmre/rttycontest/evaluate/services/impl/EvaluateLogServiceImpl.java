package mx.fmre.rttycontest.evaluate.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.evaluate.services.IEvaluateLogService;
import mx.fmre.rttycontest.evaluate.services.IEvaluateService;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;

@Service
public class EvaluateLogServiceImpl implements IEvaluateLogService {
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IEvaluateService evaluateService;
	@Autowired private IConteoRepository conteoRepository;

	@Override
	public void evaluateLog(Long logId, Integer conteoId) {
		ContestLog contestLog = contestLogRepository.findById(logId).orElse(null);
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		evaluateService.findForErrorsOnQsosOfLog(contestLog, conteo);
	}
}