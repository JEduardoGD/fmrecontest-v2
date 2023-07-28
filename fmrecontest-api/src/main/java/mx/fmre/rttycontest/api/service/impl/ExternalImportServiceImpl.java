package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.service.ExternalImportService;
import mx.fmre.rttycontest.persistence.model.ExternalLogs;
import mx.fmre.rttycontest.persistence.model.ExternalQso;
import mx.fmre.rttycontest.persistence.repository.IExternalLogsRepository;
import mx.fmre.rttycontest.persistence.repository.IExternalQsoRepository;

@Service
public class ExternalImportServiceImpl implements ExternalImportService {

    @Autowired
    IExternalQsoRepository externalQsoRepository;
    @Autowired
    IExternalLogsRepository externalLogsRepository;

    @Override
    public boolean importExternal() {

        List<ExternalLogs> externalLogsList = externalLogsRepository.findAll();
        List<ExternalQso> externalQsoList = externalQsoRepository.findAll();

        for (ExternalLogs externalLog : externalLogsList) {
            List<ExternalQso> qsosOfLog = externalQsoList.stream()
                    .filter(s -> s.getExternalQsoKey().getCallsing1().equals(externalLog.getCallsign()))
                    .collect(Collectors.toList());
            System.out.println();
        }

        return true;
    }
}
