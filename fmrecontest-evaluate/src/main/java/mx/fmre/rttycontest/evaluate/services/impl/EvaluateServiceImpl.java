package mx.fmre.rttycontest.evaluate.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.qsoevaluation.service.IEvaluateQso;
import mx.fmre.rttycontest.bs.util.DateTimeUtil;
import mx.fmre.rttycontest.evaluate.services.IEvaluateService;
import mx.fmre.rttycontest.persistence.model.CatFrequencyBand;
import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.model.RelQsoConteoQsoError;
import mx.fmre.rttycontest.persistence.repository.ICatQsoErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.ILastEmailRepository;
import mx.fmre.rttycontest.persistence.repository.IRelConteoContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoQsoErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoRepository;

@Service
@Slf4j
public class EvaluateServiceImpl implements IEvaluateService {

	@Autowired private IEditionRepository              editionRepository;
	@Autowired private IContestLogRepository           contestLogRepository;
	@Autowired private IContestQsoRepository           contestQsoRepository;
	@Autowired private ApplicationContext              appContext;
	@Autowired private ILastEmailRepository            lastEmailRepository;
	@Autowired private IConteoRepository               conteoRepository;
	@Autowired private ICatQsoErrorRepository          catQsoErrorRepository;
	@Autowired private IRelQsoConteoRepository         relQsoConteoRepository;
	@Autowired private IRelQsoConteoQsoErrorRepository relQsoConteoQsoErrorRepository;
	@Autowired private IRelConteoContestLogRepository  relConteoContestLogRepository;

	@Override
	public void findForErrorsOnQsos() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			IEvaluateQso dxccServiceQrz = appContext.getBean(edition.getQsoValidationImpl(), IEvaluateQso.class);
			
			Conteo conteo = new Conteo();
			conteo.setDatetime(DateTimeUtil.getUtcTimeDate());
			conteo.setDescription(null);
			conteo.setEdition(edition);
			Conteo savedConteo = conteoRepository.save(conteo);
			
			List<CatQsoError> catQsoErrors = catQsoErrorRepository.findByEdition(edition);
			List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(edition.getId());

			List<Integer> lastLogsIdsList = lastEmails
					.stream()
					.map(LastEmail::getContestLogId)
					.collect(Collectors.toList());
			
			List<ContestLog> logs = contestLogRepository.findByEdition(edition);
			List<ContestLog> filteredLogs = logs
					.stream()
					.filter(l -> lastLogsIdsList.contains(l.getId().intValue()))
					.collect(Collectors.toList());

			for(ContestLog contestLog: filteredLogs) {
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
				for(ContestQso qso: qsos) {
					boolean qsoComplete = true;
					CatFrequencyBand frequencyBand = qso.getFrequencyBand();
					if(frequencyBand == null) {
						log.error("El qso {} no tiene frecuencia-banda", qso.getId());
						qsoComplete = false;
					}
					if(qso.getDxccNotFound() == true) {
						log.error("El qso {} no tiene entidad dxcc", qso);
						qsoComplete = false;
					}
					RelQsoConteo relQsoConteo = new RelQsoConteo();
					relQsoConteo.setComplete(qsoComplete);
					relQsoConteo.setConteo(savedConteo);
					relQsoConteo.setContestQso(qso);
					relQsoConteo.setDatetime(DateTimeUtil.getUtcTimeDate());
					relQsoConteo.setPoints(null);
//					relQsoConteo.setMultiply(null);
					relQsoConteoRepository.save(relQsoConteo);
					
					List<CatQsoError> resEvaluation = dxccServiceQrz.findForErrors(edition, contestLog, qso, catQsoErrors);
					if(resEvaluation.isEmpty())
						continue;
					List<RelQsoConteoQsoError> relQsoConteoQsoErrorList = resEvaluation.stream().map(x -> {
						RelQsoConteoQsoError relQsoConteoQsoError = new RelQsoConteoQsoError();
						relQsoConteoQsoError.setCatQsoError(x);
						relQsoConteoQsoError.setDatetime(DateTimeUtil.getUtcTimeDate());
						relQsoConteoQsoError.setRelQsoConteo(relQsoConteo);
						return relQsoConteoQsoError;
					}).collect(Collectors.toList());
					relQsoConteoQsoErrorRepository.saveAll(relQsoConteoQsoErrorList);
				}
			}
		}
	}

	@Override
	public void setPointsForQssos() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			Conteo conteo = conteoRepository.getLastForEdition(edition);
			
			IEvaluateQso dxccServiceQrz = appContext.getBean(edition.getQsoValidationImpl(), IEvaluateQso.class);
			
			List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(edition.getId());

			List<Integer> lastLogsIdsList = lastEmails
					.stream()
					.map(LastEmail::getContestLogId)
					.collect(Collectors.toList());
			
			List<ContestLog> logs = contestLogRepository.findByEdition(edition);
			List<ContestLog> filteredLogs = logs
					.stream()
					.filter(l -> lastLogsIdsList.contains(l.getId().intValue()))
					.collect(Collectors.toList());

			for(ContestLog contestLog: filteredLogs) {
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
				qsos = contestQsoRepository.findByContestLog(contestLog);
				List<RelQsoConteo> relQsoConteos = qsos.stream().map(qso -> {
					Integer points = dxccServiceQrz.getPoints(contestLog, qso);
					RelQsoConteo relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo);
					relQsoConteo.setPoints(points);
					return relQsoConteo;
				}).collect(Collectors.toList());
				relQsoConteoRepository.saveAll(relQsoConteos);
			}
		}
	}

	@Override
	public void setMultipliesQsos() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			Conteo conteo = conteoRepository.getLastForEdition(edition);
			
			IEvaluateQso dxccServiceQrz = appContext.getBean(edition.getQsoValidationImpl(), IEvaluateQso.class);
			
			List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(edition.getId());

			List<Integer> lastLogsIdsList = lastEmails
					.stream()
					.map(LastEmail::getContestLogId)
					.collect(Collectors.toList());
			
			List<ContestLog> logs = contestLogRepository.findByEdition(edition);
			List<ContestLog> filteredLogs = logs
					.stream()
					.filter(l -> lastLogsIdsList.contains(l.getId().intValue()))
					.collect(Collectors.toList());

			for(ContestLog contestLog: filteredLogs) {
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
				qsos = contestQsoRepository.findByContestLog(contestLog);
				dxccServiceQrz.setMultiplies(conteo, qsos);
			}
		}
	}

	@Override
	public void evaluateActiveEditions() {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			Conteo conteo = conteoRepository.getLastForEdition(edition);
			
			List<LastEmail> lastEmails = lastEmailRepository.findByEditionId(edition.getId());

			List<Integer> lastLogsIdsList = lastEmails
					.stream()
					.map(LastEmail::getContestLogId)
					.collect(Collectors.toList());
			
			List<ContestLog> logs = contestLogRepository.findByEdition(edition);
			List<ContestLog> filteredLogs = logs
					.stream()
					.filter(l -> lastLogsIdsList.contains(l.getId().intValue()))
					.collect(Collectors.toList());

			for(ContestLog contestLog: filteredLogs) {
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);

				List<RelQsoConteo> listRelQsoConteo = qsos
						.stream()
						.map(qso -> relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo))
						.collect(Collectors.toList());
				Integer sumOfPoints = listRelQsoConteo
						.stream()
						.filter(rqc -> rqc.getPoints() != null)
						.collect(Collectors.summingInt(RelQsoConteo::getPoints));
				
				RelQsoConteo relQsoConteoNoComplete = listRelQsoConteo
						.stream()
						.filter(rqc -> !rqc.isComplete()).findFirst().orElse(null);
				
				int multipliers = listRelQsoConteo
						.stream()
						.filter(rqc -> rqc.isMultiply())
						.collect(Collectors.toList())
						.size();
				long totalPoints = sumOfPoints * multipliers;
				
				RelConteoContestLog relConteoContestLog = new RelConteoContestLog();
				relConteoContestLog.setConteo(conteo);
				relConteoContestLog.setContestLog(contestLog);
				relConteoContestLog.setSumOfPoints(sumOfPoints);
				relConteoContestLog.setMultipliers(multipliers);
				relConteoContestLog.setTotalPoints(totalPoints);
				relConteoContestLog.setComplete(relQsoConteoNoComplete == null);
				relConteoContestLogRepository.save(relConteoContestLog);
			}
		}
	}
}
