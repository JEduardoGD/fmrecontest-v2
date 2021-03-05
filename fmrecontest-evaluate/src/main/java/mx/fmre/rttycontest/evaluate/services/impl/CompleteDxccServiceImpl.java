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
import mx.fmre.rttycontest.bs.frequency.service.IFrequencyBsService;
import mx.fmre.rttycontest.bs.util.DateTimeUtil;
import mx.fmre.rttycontest.evaluate.services.ICompleteDxccService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.dxcc.dao.DxccEntityCallsignDAO;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.persistence.repository.ILastEmailRepository;

@Slf4j
@Service
public class CompleteDxccServiceImpl implements ICompleteDxccService {

	@Autowired private IEditionRepository              editionRepository;
	@Autowired private IEmailRepository                emailRepository;
	@Autowired private IContestLogRepository           contestLogRepository;
	@Autowired private IContestQsoRepository           contestQsoRepository;
	@Autowired private IDxccEntityRepository           dxccEntityRepository;
	@Autowired private ILastEmailRepository            lastEmailRepository;
	@Autowired private IFrequencyBsService             frequencyService;
	@Autowired private ExternalDxccService             externalDxccService;
	
	@Value("${messages.perminute}")
	private Integer messagesPerminute;
	
	@Override
	public void completeDxccEntityQsos() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(edition.getId());
			List<Integer> lastEmailIdsList = lastEmails
					.stream()
					.map(LastEmail::getEmailId)
					.collect(Collectors.toList());
			
			Map<String, DxccEntity> map = this.fillDxccMap(edition);
			
			List<Email> emails = emailRepository.getAllWithLogfileByEditionWithoutDxcc(edition);
			List<Email> filtered = emails
					.stream()
					.filter(e -> lastEmailIdsList.contains(e.getId()))
					.collect(Collectors.toList());

			Date startDate = new Date();
			int current = 1;
			
			for (Email email : filtered) {
				ContestLog contestLog = contestLogRepository.findByEmail(email);
				List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
				List<ContestQso> f = contestQsos
						.stream()
						.filter(qso -> (qso.getError() == null || qso.getError().booleanValue() == false))
						.filter(qso -> (qso.getDxccEntity() == null))
						.collect(Collectors.toList());
				List<ContestQso> newQsos = f.stream().map(qso -> {
					DxccEntity dxccEntity = null;
					try {
						dxccEntity = getDxccOf(map, qso.getCallsignr(), edition);
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
				log.info("{} de {}; time remaining: {}", current, filtered.size(),
						DateTimeUtil.timeRemaining(startDate, current++, filtered.size()));
			}
		}
	}

	@Override
	public void completeDxccEntityLogs() {
		Date startDate = new Date();
		int current = 1;
		
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(edition.getId());
			List<Integer> lastLogsIdsList = lastEmails
					.stream()
					.map(LastEmail::getContestLogId)
					.collect(Collectors.toList());
			
			Map<String, DxccEntity> map = this.fillDxccMap(edition);
			List<ContestLog> logs = contestLogRepository.getContestLogWithoutDxccEntityByEdition(edition);
			List<ContestLog> filteredLogs = logs
					.stream()
					.filter(l -> lastLogsIdsList.contains(l.getId().intValue()))
					.collect(Collectors.toList());
			for(ContestLog contestLog: filteredLogs) {
				try {
                    DxccEntity dxccEntity = null;
                    if (null != contestLog.getCallsign() && !"".equals(contestLog.getCallsign())) {
                        dxccEntity = this.getDxccOf(map, contestLog.getCallsign(), edition);
                    }
					if(dxccEntity != null) {
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
				log.info("{} de {}; time remaining: {}", current, filteredLogs.size(),
						DateTimeUtil.timeRemaining(startDate, current++, filteredLogs.size()));
			}
		}
	}
	
	private Map<String, DxccEntity> fillDxccMap(Edition edition) {
		Map<String, DxccEntity> map = new HashMap<>();
		List<DxccEntityCallsignDAO> listObjectsQsos = dxccEntityRepository.getAllByEditionOnQso(edition);
		listObjectsQsos.stream().forEach(o -> map.put(o.getCallsign(), o.getDxccEntity()));
		List<DxccEntityCallsignDAO> listObjectsLogs = dxccEntityRepository.getAllByEditionOnLogs(edition);
		listObjectsLogs.stream().forEach(o -> map.put(o.getCallsign(), o.getDxccEntity()));
		return map;
	}

	private DxccEntity getDxccOf(Map<String, DxccEntity> map, String callsign, Edition edition) throws FmreContestException {
		DxccEntity dxccEntity;
		
		// 1st atempt, from de map.
		dxccEntity = map.get(callsign);
		if(dxccEntity != null) {
			log.info("{} from map", callsign);
			return dxccEntity;
		}
		
		// 2nd and 3rd atempts, from external services
		dxccEntity = externalDxccService.getDxccFromExternalServicesByCallsign(callsign);
		
		if(dxccEntity != null) {
			log.info("{} from external Services", callsign);
			map.put(callsign, dxccEntity);
			return dxccEntity;
		}
		
		return null;
	}

	@Override
	public void completeBandsOnQsos() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(edition.getId());
			List<Integer> lastEmailIdsList = lastEmails
					.stream()
					.map(LastEmail::getEmailId)
					.collect(Collectors.toList());
			
			List<Email> emails = emailRepository.findByEdition(edition);
			List<Email> filtered = emails
					.stream()
					.filter(e -> lastEmailIdsList.contains(e.getId()))
					.collect(Collectors.toList());

			Date startDate = new Date();
			int current = 1;
			
			for (Email email : filtered) {
				ContestLog contestLog = contestLogRepository.findByEmail(email);
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
				qsos = qsos
						.stream()
						.filter(q -> q.getBand() == null)
						.filter(q -> q.getError() == null || q.getError().booleanValue() == false)
						.collect(Collectors.toList());
				List<ContestQso> newQsos = qsos.stream().map(qso -> {
					BigDecimal bdFrequency = BigDecimal.valueOf(qso.getFrequency());
					bdFrequency = bdFrequency.divide(BigDecimal.valueOf(1000));
					CatBand band = frequencyService.getFrequencyBandOf(bdFrequency);
					if (band != null) {
						qso.setBand(band);
					} else 
						log.warn("Frequency not found for freq {} on qso id {}", bdFrequency, qso.getId());
					return qso;
				}).collect(Collectors.toList());
				contestQsoRepository.saveAll(newQsos);

				log.info("{} de {}; time remaining: {}", current, filtered.size(),
						DateTimeUtil.timeRemaining(startDate, current++, filtered.size()));
			}
		}
	}
}



















