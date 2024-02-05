package mx.fmre.rttycontest.evaluate.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.service.ExternalDxccService;
import mx.fmre.rttycontest.bs.dxcc.util.DxccUtil;
import mx.fmre.rttycontest.bs.frequency.service.IFrequencyBsService;
import mx.fmre.rttycontest.bs.util.DateTimeUtil;
import mx.fmre.rttycontest.bs.util.LogsListUtil;
import mx.fmre.rttycontest.evaluate.services.ICompleteDxccService;
import mx.fmre.rttycontest.evaluate.services.IGeneralServiceUtils;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.persistence.repository.IRelExternallogEditionRepository;

@Slf4j
@Service
public class CompleteDxccServiceImpl extends LogsListUtil implements ICompleteDxccService {

	@Autowired private IEditionRepository               editionRepository;
	@Autowired private IEmailRepository                 emailRepository;
	@Autowired private IContestLogRepository            contestLogRepository;
	@Autowired private IContestQsoRepository            contestQsoRepository;
	@Autowired private IDxccEntityRepository            dxccEntityRepository;
	@Autowired private IFrequencyBsService              frequencyService;
	@Autowired private ExternalDxccService              externalDxccService;
	@Autowired private IGeneralServiceUtils             generalServiceUtils;
	@Autowired private IRelExternallogEditionRepository relExternallogEditionRepository;
	
	@Value("${messages.perminute}")
	private Integer messagesPerminute;
    
    @Override
    public void completeDxccEntityQsos() {
        List<Edition> editions = editionRepository.getActiveEditionOfContest();
        for (Edition edition : editions) {
            
            Map<String, DxccEntity> map = DxccUtil.fillDxccMap(dxccEntityRepository, edition);
            
            Date startDate = new Date();
            int current = 1;
            
            
            //for internal logs
            List<Email> emailsOfEdition = emailRepository.specialQuery(edition);
            List<Email> filtered = generalServiceUtils.filter(emailsOfEdition, edition);
            
            List<ContestLog> contestLogToEvaluate = getContexLogToEvaluate(relExternallogEditionRepository, contestLogRepository, filtered, edition);
            
            for (ContestLog contestLog : contestLogToEvaluate) {
                List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
                List<ContestQso> f = contestQsos
                        .stream()
                        .filter(qso -> (qso.getError() == null || qso.getError().booleanValue() == false))
                        .filter(qso -> (qso.getDxccEntity() == null))
                        .collect(Collectors.toList());
                List<ContestQso> newQsos = f.stream().map(qso -> {
                    DxccEntity dxccEntity = null;
                    try {
                        dxccEntity = DxccUtil.getDxccOf(externalDxccService, map, qso.getCallsignr(), edition);
                        if (dxccEntity == null)
                            qso.setDxccNotFound(true);
                        else
                            qso.setDxccNotFound(false);
                        qso.setDxccEntity(dxccEntity);
                    } catch (FmreContestException e) {
                        e.printStackTrace();
                    }
                    return qso;
                }).collect(Collectors.toList());
                contestQsoRepository.saveAll(newQsos);
                log.info("{} de {}; time remaining: {}", current, contestLogToEvaluate.size(),
                        DateTimeUtil.timeRemaining(startDate, current++, contestLogToEvaluate.size()));
            }
        }
    }

    @Override
    public void completeDxccEntityLogs() {
        Date startDate = new Date();
        int current = 1;
        
        List<Edition> editions = editionRepository.getActiveEditionOfContest();

        for (Edition edition : editions) {
            
            Map<String, DxccEntity> map = DxccUtil.fillDxccMap(dxccEntityRepository, edition);
            List<Email> emailsOfEdition = emailRepository.specialQuery(edition);
            List<Email> emailsFiltered = generalServiceUtils.filter(emailsOfEdition, edition);
            
            List<ContestLog> contestLogToEvaluate = getContexLogToEvaluate(relExternallogEditionRepository, contestLogRepository, emailsFiltered, edition);

            for (ContestLog contestLog : contestLogToEvaluate) {
                try {
                    DxccEntity dxccEntity = null;
                    if (null != contestLog.getCallsign() && !"".equals(contestLog.getCallsign())) {
                        dxccEntity = DxccUtil.getDxccOf(externalDxccService, map, contestLog.getCallsign(), edition);
                    }
                    if (dxccEntity != null) {
                        contestLog.setDxccEntity(dxccEntity);
                        contestLog.setDxccNotFound(false);
                    } else {
                        contestLog.setDxccEntity(null);
                        contestLog.setDxccNotFound(true);
                    }
                    contestLogRepository.save(contestLog);
                } catch (FmreContestException e) {
                    e.printStackTrace();
                }
                log.info("{} de {}; time remaining: {}", current, contestLogToEvaluate.size(),
                        DateTimeUtil.timeRemaining(startDate, current++, contestLogToEvaluate.size()));
            }
        }
    }    
	
	private CatBand gettingBandOfFrequency(Map<Integer, CatBand> mapFrequencyMaps, Integer frequency) {
	    if(mapFrequencyMaps.containsKey(frequency)) {
	        return mapFrequencyMaps.get(frequency);
	    }
	    CatBand band = frequencyService.getBandOfFrequency(BigDecimal.valueOf(frequency));
        if (band != null) {
            mapFrequencyMaps.put(frequency, band);
            return band;
        }
        return null;
	}
	
    @Override
    public void completeBandsOnQsos() {
        Map<Integer, CatBand> mapFrequencyMaps = new HashMap<>();
        List<Edition> editions = editionRepository.getActiveEditionOfContest();
        for (Edition edition : editions) {

            Date startDate = new Date();
            int current = 1;
            
            List<Email> emailsOfEdition = emailRepository.specialQuery(edition);
            
            List<Email> filtered = generalServiceUtils.filter(emailsOfEdition, edition);
            
            List<ContestLog> contestLogToEvaluate = getContexLogToEvaluate(relExternallogEditionRepository, contestLogRepository, filtered, edition);

            for (ContestLog contestLog : contestLogToEvaluate) {
                List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
                qsos = qsos.stream().filter(q -> q.getBand() == null)
                        .filter(q -> q.getError() == null || q.getError().booleanValue() == false)
                        .collect(Collectors.toList());
                if (qsos.isEmpty()) {
                    continue;
                }
                List<ContestQso> newQsos = qsos.stream().map(qso -> {
//					bdFrequency = bdFrequency.divide(BigDecimal.valueOf(1000));
                    CatBand catBand = gettingBandOfFrequency(mapFrequencyMaps, qso.getFrequency());
                    if (catBand == null) {
                        log.warn("Band not found in catalog for freq {} on qso id {}", qso.getFrequency(), qso.getId());
                    }
                    qso.setBand(catBand);
                    return qso;
                }).collect(Collectors.toList());
                contestQsoRepository.saveAll(newQsos);

                log.info("{} de {}; time remaining: {}", current, contestLogToEvaluate.size(),
                        DateTimeUtil.timeRemaining(startDate, current++, contestLogToEvaluate.size()));
            }
        }
    }
}



















