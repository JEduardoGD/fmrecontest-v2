package mx.fmre.rttycontest.bs.qsoevaluation.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.qsoevaluation.service.IEvaluateQso;
import mx.fmre.rttycontest.persistence.model.CatFrequencyBand;
import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.repository.ICatFrequencyBandRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoRepository;

@Service("evaluateQsoRtty2020")
public class EvaluateQsoRtty2020Impl implements IEvaluateQso {
	
	@Autowired private IDxccEntityRepository       dxccEntityRepository;
	@Autowired private ICatFrequencyBandRepository catFrequencyBandRepository;
	@Autowired private IRelQsoConteoRepository     relQsoConteoRepository;
	
	private DxccEntity mexicoDxccEntity;
	private List<String> allowedMexicoEntities;
	private String patternForNoMexicano = "^\\d+$";
	private List<CatFrequencyBand> prohibitedWarcBands;
	private List<CatFrequencyBand> frequencyBandsAllowed;
	
	@PostConstruct private void fillMexicoDxccEntity() {
		this.mexicoDxccEntity = dxccEntityRepository
				.findById(Long.valueOf(50)
				.longValue())
				.orElse(null);
		
		this.allowedMexicoEntities = Arrays.asList("AGS", "BC", "BCS", "CAM", "CHS", "CHH", "COA", "COL", "CDMX", "EMX",
				"DGO", "GTO", "GRO", "HGO", "JAL", "MIC", "MOR", "NAY", "NL", "OAX", "PUE", "QRO", "QTR", "SLP", "SIN",
				"SON", "TAB", "TMS", "TLX", "VER", "YUC", "ZAC");
		
		prohibitedWarcBands = catFrequencyBandRepository.findByBands(Arrays.asList("12 meters", "17 meters", "30 meters"));
		frequencyBandsAllowed = catFrequencyBandRepository.findByBands(Arrays.asList("80 meters", "60 meters", "40 meters", "20 meters", "15 meters", "10 meters"));
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
		
		CatQsoError error_NOT_VALID_EXCHANGE_EMMITED = qsoErrors
				.stream()
				.filter(e -> "NOT_VALID_EXCHANGE_EMMITED".equals(e.getKey()))
				.findFirst().orElse(null);
		
		CatQsoError error_QSO_ON_WARC_BAND = qsoErrors
				.stream()
				.filter(e -> "QSO_ON_WARC_BAND".equals(e.getKey()))
				.findFirst().orElse(null);

		
		if(calendarQso.before(calendarEditionStartDate)) {
			listErrors.add(error_QSO_MADE_BEFORE_CONTEST_START);
		}
		
		if(calendarQso.after(calendarEditionEndDate)) {
			listErrors.add(error_QSO_MADE_AFTER_CONTEST_START);
		}
		
		CatFrequencyBand frequencyBand = qso.getFrequencyBand();
		CatFrequencyBand qsoFrequencyBand = null;
		if (frequencyBand != null) {
			Integer qsoFrequencyBandId = frequencyBand.getId();
			qsoFrequencyBand = catFrequencyBandRepository.findById(qsoFrequencyBandId).orElse(null);
			if (!frequencyBandsAllowed.contains(qsoFrequencyBand)) {
				listErrors.add(error_QSO_OUT_OF_BAND);
			}
		}
		
		String qsoExchangeEmmited = qso.getExchangee();
		
		Long contestLogDxccEntityId = contestLog.getDxccEntity().getId();
		DxccEntity contestLogDxccEntity = dxccEntityRepository.findById(contestLogDxccEntityId).orElse(null);
		
		if(contestLogDxccEntity.equals(this.mexicoDxccEntity)) {
			if(!allowedMexicoEntities.contains(qso.getExchangee())) {
				listErrors.add(error_NOT_VALID_EXCHANGE_EMMITED);
			}
		} else {
			if(!qsoExchangeEmmited.matches(patternForNoMexicano)) {
				listErrors.add(error_NOT_VALID_EXCHANGE_EMMITED);
			}
		}
		
		if (qsoFrequencyBand != null) {
			if (prohibitedWarcBands.contains(qsoFrequencyBand)) {
				listErrors.add(error_QSO_ON_WARC_BAND);
			}
		}
		
		return listErrors;
	}

	@Override
	public Integer getPoints(ContestLog contestLog, ContestQso qso) {
		Long dxccEntityHomeId = contestLog.getDxccEntity().getId();
		if(dxccEntityHomeId == null)
			return null;
		DxccEntity dxccEntityHome = dxccEntityRepository.findById(dxccEntityHomeId).orElse(null);
		
		Long dxccEntityCalledId = qso.getDxccEntity().getId();
		if(dxccEntityCalledId == null)
			return null;
		DxccEntity dxccEntityCalled = dxccEntityRepository.findById(dxccEntityCalledId).orElse(null);
		
		if(mexicoDxccEntity.equals(dxccEntityHome)) {
			// mexicano llama a
			if(mexicoDxccEntity.equals(dxccEntityCalled)) {
				//mexicano
				return 4;
			} else {
				//extranjero
				return 3;
			}
		} else {
			// extranjero llama a
			if(mexicoDxccEntity.equals(dxccEntityCalled)) {
				//mexicano
				return 3;
			} else {
				//extranjero
				return 3;
			}
		}
	}

	@Override
	public void setMultiplies(Conteo conteo, List<ContestQso> qsos) {
		List<String> multpliesList = new ArrayList<>();
		List<RelQsoConteo> listRelQsoConteo = new ArrayList<>();
		for(ContestQso qso:qsos) {
			String exchangeR = qso.getExchanger();
			try {
				Integer exchangeRInteger = Integer.valueOf(exchangeR);
				exchangeR = exchangeRInteger.intValue() + "";
			}catch(NumberFormatException nfe) {
				
			}
			RelQsoConteo relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo);
			if(multpliesList.contains(exchangeR)) {
				relQsoConteo.setMultiply(false);
			}else {
				relQsoConteo.setMultiply(true);
				multpliesList.add(exchangeR);
			}
			listRelQsoConteo.add(relQsoConteo);
		}
		relQsoConteoRepository.saveAll(listRelQsoConteo);
	}
}
