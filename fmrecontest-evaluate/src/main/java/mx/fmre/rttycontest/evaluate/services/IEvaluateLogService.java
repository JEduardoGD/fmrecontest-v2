package mx.fmre.rttycontest.evaluate.services;

import mx.fmre.rttycontest.persistence.model.DxccEntity;

public interface IEvaluateLogService {
    void evaluateLog(DxccEntity mexicoDxccEntity, Long logId, Integer conteoId);
}
