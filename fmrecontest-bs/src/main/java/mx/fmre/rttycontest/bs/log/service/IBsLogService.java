package mx.fmre.rttycontest.bs.log.service;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.ContestLog;

public interface IBsLogService {

    List<ContestLog> getValidLogsOfEdition(int editionId);

}
