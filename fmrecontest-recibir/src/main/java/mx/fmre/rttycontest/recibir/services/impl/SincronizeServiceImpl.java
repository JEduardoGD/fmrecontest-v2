package mx.fmre.rttycontest.recibir.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.model.EmailStatus;
import mx.fmre.rttycontest.persistence.repository.ICatEmailErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailEstatusRepository;
import mx.fmre.rttycontest.persistence.repository.IEmailRepository;
import mx.fmre.rttycontest.recibir.dto.RemoteLog;
import mx.fmre.rttycontest.recibir.exception.ExternalConnectionException;
import mx.fmre.rttycontest.recibir.services.ExternalConnectionService;
import mx.fmre.rttycontest.recibir.services.SincronizeService;

@Service
@Slf4j
public class SincronizeServiceImpl implements SincronizeService {
	@Autowired
	ExternalConnectionService externalConnectionService;

	@Autowired private IEmailEstatusRepository emailEstatusRepository;
	@Autowired private IEditionRepository editionRepository;
	@Autowired private IEmailRepository emailRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	//@Autowired private IContestQsoRepository contestQsoRepository;
	@Autowired private ICatEmailErrorRepository emailErrorRepository;

	List<EmailStatus> listEstatuses;

	@PostConstruct
	private void init() {
		EmailStatus emailEstatusParsed = emailEstatusRepository.findByStatus("PARSED");
		listEstatuses = Arrays.asList(emailEstatusParsed);
	}

	@Override
	public void sincronize(){

		List<RemoteLog> allLocales = new ArrayList<>();

		List<Edition> editions = editionRepository.getActiveEditionOfContest();
		for (Edition edition : editions) {
			List<Email> emails = emailRepository.findByEditionAndEmailStatusesAndVerifiedAndAnswered(edition,
					listEstatuses);
			List<Integer> idsEmailWithError = new ArrayList<>();
			for (Email email : emails) {
				List<CatEmailError> errors = emailErrorRepository.getErrorsOfEmail(email);
				if (errors != null && !errors.isEmpty()) {
					idsEmailWithError.add(email.getId());
					continue;
				}
				
				ContestLog contestLog = contestLogRepository.findByEmail(email);
				RemoteLog remoteLog = new RemoteLog(contestLog);
				remoteLog.setCallsign(contestLog.getCallsign());
				remoteLog.setAnio(edition.getYear() + "");
				allLocales.add(remoteLog);
			}
			if (!idsEmailWithError.isEmpty()) {
				log.info("no se agrega a la lista de archivos a sincronizar por tener errorres en el correo: {}",
						idsEmailWithError);
			}

			if (allLocales == null || allLocales.isEmpty()) {
				log.warn("No hay registros locales que podrian faltar de guardar");
				continue;
			}

			// 1. Get all saved logs in external
			RemoteLog[] allSavedByYearArr;
			try {
				allSavedByYearArr = externalConnectionService.getAllByYear(edition.getYear());
			} catch (ExternalConnectionException e) {
				log.error("Error al ejecutar el metodo externalConnectionService.getAllByYear: {}", e.getMessage());
				return;
			}
			if (allSavedByYearArr == null) {
				log.warn("La repsuesta a la consulta de registros externos fue nula");
				return;
			}
			if (allSavedByYearArr.length <= 0) {
				log.warn("La repsuesta a la consulta de registros externos no tiene registros");
			}

			// 2. Convertir arreglo a lista
			List<RemoteLog> allSavedByYearList = Arrays.asList(allSavedByYearArr);

			// 3. Remover de remoto todos los remotos de la lista local
			allLocales.removeAll(allSavedByYearList);
			
			StringBuffer sb = new StringBuffer();
			for (RemoteLog l : allLocales) {
				sb.append(String.format("{%s|%s}", l.getAnio(), l.getCallsign())).append(" ");
			}

			// 4. Los que queden vivos faltan de ser guardar
			log.info("Faltan de guardar: {}", sb.toString());
			
			if (allLocales != null && !allLocales.isEmpty()) {
				RemoteLog localAGuardar = allLocales.get(0);
				log.info("Se va a guardar el local: {}", localAGuardar.toString());
				try {
					Long nextId = externalConnectionService.getNextId();
					if (nextId == null) {
						nextId = 1l;
					}
					localAGuardar.setId(nextId);
					localAGuardar = externalConnectionService.save(localAGuardar);
					log.info("Guardado: {}", localAGuardar);
				} catch (ExternalConnectionException e) {
					log.error(e.getMessage());
				}
			}
		}
	}
}
