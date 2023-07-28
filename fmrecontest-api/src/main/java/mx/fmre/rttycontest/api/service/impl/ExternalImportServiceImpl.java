package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.service.ExternalImportService;
import mx.fmre.rttycontest.api.util.ExternalParser;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.ExternalLogs;
import mx.fmre.rttycontest.persistence.model.ExternalQso;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IExternalLogsRepository;
import mx.fmre.rttycontest.persistence.repository.IExternalQsoRepository;

@Service
public class ExternalImportServiceImpl implements ExternalImportService {

    @Autowired IExternalQsoRepository  externalQsoRepository;
    @Autowired IExternalLogsRepository externalLogsRepository;
    @Autowired IContestLogRepository   contestLogRepository;

    @Override
    public boolean importExternal() {

        List<ExternalLogs> externalLogsList = externalLogsRepository.findAll();
        List<ExternalQso> externalQsoList = externalQsoRepository.findAll();

        for (ExternalLogs externalLog : externalLogsList) {
            List<ExternalQso> externalQsoListFiltered = externalQsoList.stream()
                    .filter(s -> s.getExternalQsoKey().getCallsing1().equalsIgnoreCase(externalLog.getCallsign()))
                    .collect(Collectors.toList());

            List<ContestQso> contestQsoList = externalQsoListFiltered.stream()
                    .map(ExternalParser::parse)
                    .collect(Collectors.toList());
            
            ContestLog contestLog = ExternalParser.parse(externalLog, contestQsoList);
            
            @SuppressWarnings("unused")
            ContestLog res = contestLogRepository.save(contestLog);
            System.out.println();

        }

        return true;
    }
}
