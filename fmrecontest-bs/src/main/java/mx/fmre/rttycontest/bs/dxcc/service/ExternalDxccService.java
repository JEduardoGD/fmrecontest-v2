package mx.fmre.rttycontest.bs.dxcc.service;

import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.DxccEntity;

public interface ExternalDxccService {

    DxccEntity getDxccFromExternalServicesByCallsign(String entityName) throws FmreContestException;

    DxccEntity getDxccFromExternalServicesByEntityName(String enntityName) throws FmreContestException;

}
