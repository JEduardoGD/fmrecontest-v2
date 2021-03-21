package mx.fmre.rttycontest.bs.qso.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.log.service.IBsLogService;
import mx.fmre.rttycontest.bs.qso.service.IBsQsosService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;

@Service
public class BsQsosServiceImpl implements IBsQsosService {
    @Autowired private IContestQsoRepository contestQsoRepository;
    @Autowired private IBsLogService logService;

    @Override
    public List<ContestQso> getValidQsosOfEdition(int editionId) throws FmreContestException {
        
        List<ContestLog> logs = logService.getValidLogsOfEdition(editionId);

        List<ContestQso> allQsos = new ArrayList<>();
        for (ContestLog log : logs) {
            List<ContestQso> qsos = contestQsoRepository.findByContestLog(log).stream()
                    .filter(qso -> qso.getError() == null || qso.getError().equals(Boolean.FALSE))
                    .filter(qso -> qso.getDxccEntity() != null).collect(Collectors.toList());
            allQsos.addAll(qsos);
        }

        return allQsos;
    }
}
