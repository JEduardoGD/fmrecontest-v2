package mx.fmre.rttycontest.api.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.api.service.ExternalImportService;
import mx.fmre.rttycontest.api.util.ExternalParser;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.ExternalLogs;
import mx.fmre.rttycontest.persistence.model.ExternalQso;
import mx.fmre.rttycontest.persistence.model.RelExternallogEdition;
import mx.fmre.rttycontest.persistence.model.RelExternallogEditionPk;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IExternalLogsRepository;
import mx.fmre.rttycontest.persistence.repository.IExternalQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IRelExternallogEditionRepository;

@Service
@Slf4j
public class ExternalImportServiceImpl implements ExternalImportService {

    @Autowired IExternalQsoRepository           externalQsoRepository;
    @Autowired IExternalLogsRepository          externalLogsRepository;
    @Autowired IContestLogRepository            contestLogRepository;
    @Autowired IContestQsoRepository            contestQsoRepository;
    @Autowired IEditionRepository               editionRepository;
    @Autowired IRelExternallogEditionRepository relExternallogEditionRepository;

    @Override
    public boolean importExternal() {
        
        Edition edition = editionRepository.findById(6).orElse(null);

        List<ExternalLogs> externalLogsList = externalLogsRepository.findAll();
        List<ExternalQso> externalQsoList = externalQsoRepository.findAll();
        int i = 1;

        for (ExternalLogs externalLog : externalLogsList) {
            List<ExternalQso> externalQsoListFiltered = externalQsoList.stream()
                    .filter(s -> s.getExternalQsoKey().getCallsing1().equalsIgnoreCase(externalLog.getCallsign()))
                    .collect(Collectors.toList());
            log.info("Parseando log externo {} de {} ({} qsos)", i++, externalLogsList.size(), externalQsoListFiltered.size());
            
            saveContestLog(edition, externalLog, externalQsoListFiltered);
            
            

        }

        return true;
    }
    
    @Transactional
    private void saveContestLog(Edition edition, ExternalLogs externalLog, List<ExternalQso> externalQsoList) {
        ContestLog contestLog = ExternalParser.parse(externalLog);
        ContestLog contestLogSaved = contestLogRepository.save(contestLog);
        List<ContestQso> contestQsoList = externalQsoList.stream()
                .map(ExternalParser::parse)
                .map(q -> {
                    q.setContestLog(contestLogSaved);
                    return q;
                })
                .collect(Collectors.toList());
        contestQsoRepository.saveAll(contestQsoList);
        
        RelExternallogEditionPk relExternallogEditionPk = new RelExternallogEditionPk(contestLog, edition);
        RelExternallogEdition relExternallogEdition = new RelExternallogEdition();
        relExternallogEdition.setRelExternallogEditionPk(relExternallogEditionPk);
        relExternallogEdition.setActive(true);
        relExternallogEdition.setDatetime(Calendar.getInstance().getTime());
        
        relExternallogEditionRepository.save(relExternallogEdition);
    }
}
