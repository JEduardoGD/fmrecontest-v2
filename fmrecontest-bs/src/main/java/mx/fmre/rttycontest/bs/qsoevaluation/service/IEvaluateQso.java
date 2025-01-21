package mx.fmre.rttycontest.bs.qsoevaluation.service;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;

public interface IEvaluateQso {
    List<CatQsoError> findForErrors(DxccEntity mexicoDxccEntity, Edition edition, ContestLog contestLog, ContestQso qso,
            List<CatQsoError> qsoErrors);
    Integer getPoints(DxccEntity mexicoDxccEntity, ContestLog contestLog, ContestQso qso);
    void setMultiplies(DxccEntity mexicoDxccEntity, Conteo conteo, List<ContestQso> qsos);
}
