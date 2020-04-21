package mx.fmre.rttycontest.evaluate.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.qsoevaluation.service.IEvaluateQso;
import mx.fmre.rttycontest.bs.util.DateTimeUtil;
import mx.fmre.rttycontest.evaluate.services.IEvaluateService;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.LastEmail;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.model.RelQsoConteoQsoError;
import mx.fmre.rttycontest.persistence.repository.ICatQsoErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
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
	@Autowired private IEmailRepository                emailRepository;
	
	@Override
	public Conteo createConteo(Edition edition, String description) {
		Conteo conteo = new Conteo();
		conteo.setDatetime(DateTimeUtil.getUtcTimeDate());
		conteo.setDescription(description);
		conteo.setEdition(edition);
		return conteoRepository.save(conteo);
	}

	@Override
	public void findForErrorsOnQsos(Conteo conteo, Edition edition) {
		IEvaluateQso dxccServiceQrz = appContext.getBean(edition.getQsoValidationImpl(), IEvaluateQso.class);
		
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

		for(ContestLog contestLog: filteredLogs)
			this.findForErrorsOnQsosOfLog(contestLog, conteo, dxccServiceQrz, edition, catQsoErrors);
	}
	
	@Override
	public void findForErrorsOnQsosOfLog(ContestLog contestLog, Conteo conteo) {
		Email email = emailRepository.findById(contestLog.getEmail().getId()).orElse(null);
		Edition edition = editionRepository.findById(email.getEdition().getId()).orElse(null);
		IEvaluateQso dxccServiceQrz = appContext.getBean(edition.getQsoValidationImpl(), IEvaluateQso.class);
		List<CatQsoError> catQsoErrors = catQsoErrorRepository.findByEdition(edition);
		this.findForErrorsOnQsosOfLog(contestLog, conteo, dxccServiceQrz, edition, catQsoErrors);
	}
	
	private void findForErrorsOnQsosOfLog(
			ContestLog contestLog,
			Conteo conteo,
			IEvaluateQso dxccServiceQrz,
			Edition edition,
			List<CatQsoError> catQsoErrors) {
		List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
		for(ContestQso qso: qsos) {
			boolean qsoComplete = true;
			CatBand qsoBand = qso.getBand();
			if (qsoBand == null) {
				if (qso.getError() != null && qso.getError().equals(Boolean.TRUE)) {

				} else {
					log.error("El qso {} no tiene banda", qso.getId());
					qsoComplete = false;
				}
			}
			if (qso.getDxccNotFound() != null && qso.getDxccNotFound() == true) {
				if (qso.getError() != null && qso.getError().equals(Boolean.TRUE)) {

				} else {
					log.error("El qso {} no tiene entidad dxcc", qso.getId());
					qsoComplete = false;
				}
			}
			RelQsoConteo relQsoConteo;
			relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo);
			if(relQsoConteo == null)
				relQsoConteo = new RelQsoConteo();
			relQsoConteo.setComplete(qsoComplete);
			relQsoConteo.setConteo(conteo);
			relQsoConteo.setContestQso(qso);
			relQsoConteo.setDatetime(DateTimeUtil.getUtcTimeDate());
			relQsoConteo.setPoints(null);
			
			relQsoConteo = relQsoConteoRepository.save(relQsoConteo);
			
			List<CatQsoError> resEvaluation = dxccServiceQrz.findForErrors(edition, contestLog, qso, catQsoErrors);
			if(resEvaluation.isEmpty())
				continue;
			
			List<RelQsoConteoQsoError> exsist = relQsoConteoQsoErrorRepository.findByRelQsoConteo(relQsoConteo);
			if(!exsist.isEmpty())
				relQsoConteoQsoErrorRepository.deleteAll(exsist);
			
			List<RelQsoConteoQsoError> relQsoConteoQsoErrorList = new ArrayList<>();
			for (CatQsoError x : resEvaluation) {
				RelQsoConteoQsoError relQsoConteoQsoError = new RelQsoConteoQsoError();
				relQsoConteoQsoError.setCatQsoError(x);
				relQsoConteoQsoError.setDatetime(DateTimeUtil.getUtcTimeDate());
				relQsoConteoQsoError.setRelQsoConteo(relQsoConteo);
				relQsoConteoQsoErrorList.add(relQsoConteoQsoError);
			}
			relQsoConteoQsoErrorRepository.saveAll(relQsoConteoQsoErrorList);
		}
	}

	@Override
	public void setPointsForQssos(Conteo conteo) {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
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
			
			int i = 1;

			for(ContestLog contestLog: filteredLogs) {
				log.info("Setting points for Log id {} ({} / {})", contestLog.getId(), i++, filteredLogs.size());
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
				qsos = contestQsoRepository.findByContestLog(contestLog);
				qsos = qsos
						.stream()
						.filter(q -> (q.getError() == null || q.getError().booleanValue() == false))
						.collect(Collectors.toList());
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
	public void setMultipliesQsos(Conteo conteo) {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
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
			
			int i = 1;

			for(ContestLog contestLog: filteredLogs) {
				log.info("Setting multipliers log id {} ({} / {})", contestLog.getId(), i++, filteredLogs.size());
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
				qsos = qsos
						.stream()
						.filter(q -> (q.getError() == null || q.getError().booleanValue() == false))
						.collect(Collectors.toList());
				qsos = contestQsoRepository.findByContestLog(contestLog);
				dxccServiceQrz.setMultiplies(conteo, qsos);
			}
		}
	}

	@Override
	public void evaluateActiveEditions(Conteo conteo) {
		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
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
			
			int i = 1;

			for(ContestLog contestLog: filteredLogs) {
				log.info("Evaluating log id {} ({} / {})", contestLog.getId(), i++, filteredLogs.size());
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
				qsos = qsos
						.stream()
						.filter(q -> (q.getError() == null || q.getError().booleanValue() == false))
						.collect(Collectors.toList());

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
						.filter(rqc -> (rqc.getDxccOrBandError() == null || rqc.getDxccOrBandError().booleanValue() == false))
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
