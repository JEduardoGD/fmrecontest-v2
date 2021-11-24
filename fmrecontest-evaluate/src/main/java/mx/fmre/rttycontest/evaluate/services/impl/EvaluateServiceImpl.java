package mx.fmre.rttycontest.evaluate.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.gridlocator.service.impl.LocatorServiceException;
import mx.fmre.rttycontest.bs.qsoevaluation.service.IEvaluateQso;
import mx.fmre.rttycontest.bs.util.DateTimeUtil;
import mx.fmre.rttycontest.evaluate.services.IEvaluateService;
import mx.fmre.rttycontest.evaluate.services.IGeneralServiceUtils;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
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

@SuppressWarnings("unused")
@Service
@Slf4j
public class EvaluateServiceImpl implements IEvaluateService {

	@Autowired private IEditionRepository              editionRepository;
	@Autowired private IContestLogRepository           contestLogRepository;
	@Autowired private IContestQsoRepository           contestQsoRepository;
	@Autowired private ApplicationContext              appContext;
	@Autowired private IConteoRepository               conteoRepository;
	@Autowired private ICatQsoErrorRepository          catQsoErrorRepository;
	@Autowired private IRelQsoConteoRepository         relQsoConteoRepository;
	@Autowired private IRelQsoConteoQsoErrorRepository relQsoConteoQsoErrorRepository;
	@Autowired private IRelConteoContestLogRepository  relConteoContestLogRepository;
	@Autowired private IEmailRepository                emailRepository;
    @Autowired private IGeneralServiceUtils            generalServiceUtils;
	
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

        List<Email> emailsOfEdition = emailRepository.specialQuery(edition);
        List<Email> emailsFiltered = generalServiceUtils.filter(emailsOfEdition, edition);
		
		Date startDate = new Date();
		int current = 1;
		
		for (Email emailFiltered : emailsFiltered) {
            ContestLog contestLog = emailFiltered.getContestLog();
			log.info("{} de {}; time remaining: {}", current, emailsFiltered.size(),
					DateTimeUtil.timeRemaining(startDate, current++, emailsFiltered.size()));
			this.findForErrorsOnQsosOfLog(contestLog, conteo, dxccServiceQrz, edition, catQsoErrors);
		}
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
            if (qso.getError() != null && qso.getError().booleanValue() == true) {
                qsoComplete = true;
            } else {
                if (qso.getDxccNotFound() != null && qso.getDxccNotFound() == true) {
                    log.error("El qso {} no tiene entidad dxcc", qso.getId());
                    qsoComplete = false;
                }
            }
			RelQsoConteo relQsoConteo;
			relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo);
			if(relQsoConteo == null) {
				relQsoConteo = new RelQsoConteo();
			}
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
			if(!exsist.isEmpty()) {
				relQsoConteoQsoErrorRepository.deleteAll(exsist);
			}
			
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
			
			List<Email> emailsOfEdition = emailRepository.specialQuery(edition);
	        List<Email> emailsFiltered = generalServiceUtils.filter(emailsOfEdition, edition);
			
			int i = 1;

            for (Email emailFiltered : emailsFiltered) {
                ContestLog contestLog = emailFiltered.getContestLog();
                log.info("Setting points for Log id {} ({} / {})", contestLog.getId(), i++, emailsFiltered.size());
                List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
                qsos = contestQsoRepository.findByContestLog(contestLog);
                qsos = qsos.stream().filter(q -> (q.getError() == null || q.getError().booleanValue() == false))
                        .collect(Collectors.toList());
                List<RelQsoConteo> relQsoConteos = qsos.stream().map(qso -> {
                    Integer points = null;
                    try {
                        points = dxccServiceQrz.getPoints(contestLog, qso);
                    } catch (LocatorServiceException e) {
                        log.error(e.getLocalizedMessage());
                    }
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
			
			List<Email> emailsOfEdition = emailRepository.specialQuery(edition);
            List<Email> emailsFiltered = generalServiceUtils.filter(emailsOfEdition, edition);

			int i = 1;

            for (Email emailFiltered : emailsFiltered) {
                ContestLog contestLog = emailFiltered.getContestLog();

                Long contestLogGroup = contestLog.getGroup();
                if (null != contestLogGroup) {
                    List<ContestLog> groupContestlogs = emailsFiltered.stream()
                            .filter(e -> e.getContestLog().getGroup() != null
                                    && e.getContestLog().getGroup().equals(contestLogGroup.longValue())
                                    && !e.getContestLog().getId().equals(contestLog.getId()))
                            .map(Email::getContestLog).collect(Collectors.toList());

                    List<ContestQso> contestQsos = contestQsoRepository.findByContestLogs(groupContestlogs);

                    List<String> workedGridLocators = contestQsos.stream().map(qso -> qso.getGridLocator())
                            .collect(Collectors.toList());
                }

                log.info("Setting multipliers log id {} ({} / {})", contestLog.getId(), i++, emailsFiltered.size());
                List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog);
                qsos = qsos.stream().filter(q -> (q.getError() == null || q.getError().booleanValue() == false))
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
		    List<Email> emailsOfEdition = emailRepository.specialQuery(edition);
            List<Email> emailsFiltered = generalServiceUtils.filter(emailsOfEdition, edition);
			
			int i = 1;

			for (Email emailFiltered : emailsFiltered) {
                ContestLog contestLog = emailFiltered.getContestLog();
				log.info("Evaluating log id {} ({} / {})", contestLog.getId(), i++, emailsFiltered.size());
				List<ContestQso> qsos = contestQsoRepository.findByContestLog(contestLog)
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
						.filter(rcq -> {
							Long qsoId = rcq.getContestQso().getId();
							ContestQso qso = contestQsoRepository.findById(qsoId).orElse(null);
							return (qso.getError() == null || qso.getError().booleanValue() == false);
						})
						.filter(rqc -> !rqc.isComplete()).findFirst()
						.orElse(null);
				
				int multipliers = listRelQsoConteo
						.stream()
						.filter(rqc -> rqc.isMultiply())
						.collect(Collectors.toList())
						.size();
				long totalPoints = sumOfPoints * (long)multipliers;
				
//				Integer emailId = contestLog.getEmail().getId();
//				List<EmailEmailError> listRelEmailEmailerror = emailEmailErrorRepository.findByEmailId(emailId);
				
				RelConteoContestLog relConteoContestLog = new RelConteoContestLog();
				relConteoContestLog.setConteo(conteo);
				relConteoContestLog.setContestLog(contestLog);
				relConteoContestLog.setSumOfPoints(sumOfPoints);
				relConteoContestLog.setMultipliers(multipliers);
				relConteoContestLog.setTotalPoints(totalPoints);
				relConteoContestLog.setComplete(relQsoConteoNoComplete == null/* && listRelEmailEmailerror.size() <= 0*/);
				relConteoContestLogRepository.save(relConteoContestLog);
			}
		}
	}
}
