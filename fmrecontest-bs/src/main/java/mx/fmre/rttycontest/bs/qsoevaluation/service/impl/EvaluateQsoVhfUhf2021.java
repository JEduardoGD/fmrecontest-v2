package mx.fmre.rttycontest.bs.qsoevaluation.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.gridlocator.service.ILocatorService;
import mx.fmre.rttycontest.bs.gridlocator.service.impl.LocatorServiceException;
import mx.fmre.rttycontest.bs.qsoevaluation.service.IEvaluateQso;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.CatGridlocatorState;
import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.repository.ICatBandRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoRepository;

@Service("evaluateQsoVhfUhf2021")
public class EvaluateQsoVhfUhf2021 implements IEvaluateQso {
	
	@Autowired private IRelQsoConteoRepository        relQsoConteoRepository;
	@Autowired private ICatBandRepository             catBandRepository;
	@Autowired private ILocatorService                locatorService;
	
	private List<String> frequencyBandsAllowed;
	
	@PostConstruct private void fillMexicoDxccEntity() {
		frequencyBandsAllowed = Arrays.asList("6 meters", "2 meters", "70 centimeters");
	}
	
	@Override
	public List<CatQsoError> findForErrors(Edition edition, ContestLog contestLog, ContestQso qso, List<CatQsoError> qsoErrors) {
		List<CatQsoError> listErrors = new ArrayList<>();
		Calendar calendarEditionStartDate = Calendar.getInstance();
		calendarEditionStartDate.setTime(edition.getStart());
		
		Calendar calendarEditionEndDate = Calendar.getInstance();
		calendarEditionEndDate.setTime(edition.getEnd());
		
		Calendar calendarQso = Calendar.getInstance();
		calendarQso.setTime(qso.getDatetime());
		
		CatQsoError error_QSO_MADE_BEFORE_CONTEST_START = qsoErrors
				.stream()
				.filter(e -> "QSO_MADE_BEFORE_CONTEST_START".equals(e.getKey()))
				.findFirst().orElse(null);
		
		CatQsoError error_QSO_MADE_AFTER_CONTEST_START = qsoErrors
				.stream()
				.filter(e -> "QSO_MADE_AFTER_CONTEST_START".equals(e.getKey()))
				.findFirst().orElse(null);
		
		CatQsoError error_QSO_OUT_OF_BAND = qsoErrors
				.stream()
				.filter(e -> "QSO_OUT_OF_BAND".equals(e.getKey()))
				.findFirst().orElse(null);
		
		if(calendarQso.before(calendarEditionStartDate)) {
			listErrors.add(error_QSO_MADE_BEFORE_CONTEST_START);
		}
		
		if(calendarQso.after(calendarEditionEndDate)) {
			listErrors.add(error_QSO_MADE_AFTER_CONTEST_START);
		}
		
		CatBand band = qso.getBand();
		CatBand qsoBand = null;
		if (band != null) {
			Integer bandId = band.getId();
			qsoBand = catBandRepository.findById(bandId).orElse(null);
			if (!frequencyBandsAllowed.contains(qsoBand.getBand())) {
				listErrors.add(error_QSO_OUT_OF_BAND);
			}
		}
		
		return listErrors;
	}

    @Override
    public Integer getPoints(ContestLog contestLog, ContestQso qso) {
        try {
            CatGridlocatorState catGridlocatorStateLog = locatorService.getGridLocatorstateOfGridlocator(contestLog.getGridlocator());
            CatGridlocatorState catGridlocatorStateQso = locatorService.getGridLocatorstateOfGridlocator(qso.getGridLocator());
            if (null == catGridlocatorStateQso || null == catGridlocatorStateQso) {
                return 0;
            }
            return catGridlocatorStateLog.isTheSame(catGridlocatorStateQso) ? 10 : 15;
        } catch (LocatorServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void setMultiplies(Conteo conteo, List<ContestQso> qsos) {
        List<String> multpliesList = new ArrayList<>();
        List<RelQsoConteo> listRelQsoConteo = new ArrayList<>();
        for (ContestQso qso : qsos) {
            String desGridLocator = qso.getGridLocator();
            RelQsoConteo relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo);

            if (qso.getDxccEntity() == null) {
                relQsoConteo.setMultiply(false);
                continue;
            }

            if (!multpliesList.contains(desGridLocator)) {
                relQsoConteo.setMultiply(true);
                multpliesList.add(desGridLocator);
            } else {
                relQsoConteo.setMultiply(false);
            }

            listRelQsoConteo.add(relQsoConteo);
        }
        relQsoConteoRepository.saveAll(listRelQsoConteo);
    }
}