package mx.fmre.rttycontest.recibir.business.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.bs.dxcc.service.ExternalDxccService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.AttachedFile;
import mx.fmre.rttycontest.persistence.model.CatEmailError;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.repository.IAttachedFileRepository;
import mx.fmre.rttycontest.persistence.repository.ICatEmailErrorRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.recibir.business.IVerificacionEmail;

@Slf4j
@Service("verificacionEmailRtty2024Impl")
public class verificacionEmailRtty2024Impl implements IVerificacionEmail {
	@Autowired private IEditionRepository       editionRepository;
	@Autowired private IAttachedFileRepository  attachedFileRepository;
	@Autowired private ICatEmailErrorRepository catEmailErrorRepository;
	@Autowired private IContestLogRepository    contestLogRepository;
	@Autowired private IContestQsoRepository    contestQsoRepository;
	@Autowired private ExternalDxccService      externalDxccService;
	
	@Value("${FMRE_CALLSIGN}")
	private String FMRE_CALLSIG;

	private final String EMAIL_WITHOUT_SUBJECT                  = "EMAIL_WITHOUT_SUBJECT";
	private final String EMAIL_WITHOUT_ATTACHED_FILES           = "EMAIL_WITHOUT_ATTACHED_FILES";
	private final String SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN = "SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN";
	private final String EMAIL_WITHOUT_CONTSTLOG                = "EMAIL_WITHOUT_CONTSTLOG";
	private final String SUBJECT_WITH_MORE_THAN_ONE_WORD        = "SUBJECT_WITH_MORE_THAN_ONE_WORD";
	private final String WRONG_CHANGE_CODE                      = "WRONG_CHANGE_CODE";
    private final String LOG_WITHOUT_CALLSIGN                   = "LOG_WITHOUT_CALLSIGN";
	
	private DxccEntity mexicoDxccEntity;
	
    @PostConstruct
    private void init() {
        try {
            mexicoDxccEntity = externalDxccService.getDxccFromExternalServicesByEntityName("mexico");
            if (mexicoDxccEntity == null) {
                mexicoDxccEntity = externalDxccService.getDxccFromExternalServicesByCallsign(FMRE_CALLSIG);
            }
        } catch (FmreContestException e) {
            log.error(e.getLocalizedMessage());
        }
    }

	@Override
	public List<CatEmailError> verify(Email email) throws FmreContestException {
		Integer editionId = email.getEdition().getId();
		Edition edition = editionRepository.findById(editionId).orElse(null);
		List<AttachedFile> attachedFiles = attachedFileRepository.findByEmail(email);

		List<CatEmailError> listCatEmailError = new ArrayList<>();

		CatEmailError x;

		/* EMAIL_WITHOUT_SUBJECT */
		x = catEmailErrorRepository.findByEditionAndDescripcion(edition, EMAIL_WITHOUT_SUBJECT);
		if (x != null) {
			if (this.verify_EMAIL_WITHOUT_SUBJECT(email, edition, attachedFiles)) {
				listCatEmailError.add(x);
			}
		} else
			throw new FmreContestException(
					"The test \"" + EMAIL_WITHOUT_SUBJECT + "\" is not found for editon with ID " + editionId);

		/* EMAIL_WITHOUT_ATTACHED_FILES */
		x = catEmailErrorRepository.findByEditionAndDescripcion(edition, EMAIL_WITHOUT_ATTACHED_FILES);
		if (x != null) {
			if (this.verify_EMAIL_WITHOUT_ATTACHED_FILES(email, edition, attachedFiles)) {
				listCatEmailError.add(x);
			}
		} else
			throw new FmreContestException(
					"The test \"" + EMAIL_WITHOUT_ATTACHED_FILES + "\" is not found for editon with ID " + editionId);

		/* SUBJECT_WITH_MORE_THAN_ONE_WORD */
		x = catEmailErrorRepository.findByEditionAndDescripcion(edition, SUBJECT_WITH_MORE_THAN_ONE_WORD);
		if (x != null) {
			if (this.verify_SUBJECT_WITH_MORE_THAN_ONE_WORD(email, edition, attachedFiles)) {
				listCatEmailError.add(x);
			}
		} else
			throw new FmreContestException(
					"The test \"" + SUBJECT_WITH_MORE_THAN_ONE_WORD + "\" is not found for editon with ID " + editionId);

		/*SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN*/
		x = catEmailErrorRepository.findByEditionAndDescripcion(edition, SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN);
		if(x != null) {
			if(this.verify_SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN(email, edition, attachedFiles)) {
				listCatEmailError.add(x);
			}
		} else
			throw new FmreContestException("The test \"" + SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN + "\" is not found for editon with ID " + editionId);

		/*SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN*/
		x = catEmailErrorRepository.findByEditionAndDescripcion(edition, EMAIL_WITHOUT_CONTSTLOG);
		if(x != null) {
			if(this.verify_EMAIL_WITHOUT_CONTSTLOG(email, edition, attachedFiles)) {
				listCatEmailError.add(x);
			}
		} else
			throw new FmreContestException("The test \"" + EMAIL_WITHOUT_CONTSTLOG + "\" is not found for editon with ID " + editionId);

		/*SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN*/
		x = catEmailErrorRepository.findByEditionAndDescripcion(edition, WRONG_CHANGE_CODE);
		if(x != null) {
			if(this.verify_WRONG_CHANGE_CODE(email, edition, attachedFiles)) {
				listCatEmailError.add(x);
			}
		} else
			throw new FmreContestException("The test \"" + WRONG_CHANGE_CODE + "\" is not found for editon with ID " + editionId);
		
		/*LOG_WITHOUT_CALLSIGN*/
        x = catEmailErrorRepository.findByEditionAndDescripcion(edition, LOG_WITHOUT_CALLSIGN);
        if(x != null) {
            if(this.verify_LOG_WITHOUT_CALLSIGN(email)) {
                listCatEmailError.add(x);
            }
        } else
            throw new FmreContestException("The test \"" + WRONG_CHANGE_CODE + "\" is not found for editon with ID " + editionId);
		

		return listCatEmailError;
	}

	private boolean verify_EMAIL_WITHOUT_SUBJECT(Email email, Edition edition, List<AttachedFile> attachedFiles) {
		return (email.getSubject() == null || "".equals(email.getSubject()));
	}

	private boolean verify_EMAIL_WITHOUT_ATTACHED_FILES(Email email, Edition edition,
			List<AttachedFile> attachedFiles) {
		return (attachedFiles == null || attachedFiles.size() <= 0);
	}

	private boolean verify_SUBJECT_WITH_MORE_THAN_ONE_WORD(Email email, Edition edition,
			List<AttachedFile> attachedFiles) {
		String subject = email.getSubject();
		if(subject != null && !"".equals(subject)) {
			String[] arr = subject.split("\\s");
			return arr.length > 1;
		}
		return false;
	}

	private boolean verify_SUBJECT_NOT_EQUALS_CONTESTLOG_CALLSIGN(Email email, Edition edition, List<AttachedFile> attachedFiles) {
		ContestLog contestLog = contestLogRepository.findByEmail(email);
		if(contestLog == null || contestLog.getCallsign() == null || email.getSubject() == null)
			return false;
		String contestLogCallsign = contestLog.getCallsign().toUpperCase();
		String subject = email.getSubject().toUpperCase();
		return !contestLogCallsign.equals(subject);
	}

	private boolean verify_EMAIL_WITHOUT_CONTSTLOG(Email email, Edition edition, List<AttachedFile> attachedFiles) {
		ContestLog contestLog = contestLogRepository.findByEmail(email);
		if(contestLog == null)
			return true;
		return false;
	}

	private boolean verify_WRONG_CHANGE_CODE(Email email, Edition edition, List<AttachedFile> attachedFiles) throws FmreContestException {
        ContestLog contestLog = contestLogRepository.findByEmail(email);
        if (null == contestLog) {
            log.error("ContestLog es nulo");
            return false;
        }

        if (contestLog.getCallsign() == null || contestLog.getCallsign().equals("")) {
            log.error("ContestLog.callsign es nulo o cadena vacía");
            return false;
        }
		
		DxccEntity dxccLogEntity = externalDxccService.getDxccFromExternalServicesByCallsign(contestLog.getCallsign());
		
		List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);

		if (!mexicoDxccEntity.equals(dxccLogEntity)) {
			// validacion para paises distintos de mexico
			boolean allExchangeAreInteger = true;
			for (ContestQso qso : contestQsos) {
				String exchangeStr = qso.getExchangee();
				try {
					Integer.parseInt(exchangeStr);
				} catch (NumberFormatException e) {
					allExchangeAreInteger = false;
					break;
				}
			}
			// verificar que todos los codigos enviados sean texto
			return !allExchangeAreInteger;
		} else {
			// validacion para mexico
			boolean allExchangeAreText = true;
			for (ContestQso qso : contestQsos) {
				String exchangeStr = qso.getExchangee();
				try {
					Integer.parseInt(exchangeStr);
					allExchangeAreText = false;
					break;
				} catch (NumberFormatException e) {
					@SuppressWarnings("unused")
					String err = e.getLocalizedMessage();
				}
			}
			return !allExchangeAreText;
		}
	}
    
    private boolean verify_LOG_WITHOUT_CALLSIGN(Email email) {
        ContestLog contestLog = contestLogRepository.findByEmail(email);
        if (null == contestLog) {
            log.error("ContestLog es nulo");
            return false;
        }

        if (contestLog.getCallsign() == null || contestLog.getCallsign().equals("")) {
            log.error("ContestLog.callsign es nulo o cadena vacía");
            return true;
        }
        return false;
    }
}











