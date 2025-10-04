package mx.fmre.rttycontest.bs.qsoevaluation.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.bs.dto.MultiplierDTO;
import mx.fmre.rttycontest.bs.qsoevaluation.service.IEvaluateQso;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.CatQsoError;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.repository.ICatBandRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoRepository;

@Service("evaluateQsoRtty2020")
public class EvaluateQsoRtty2020Impl implements IEvaluateQso {
	
	@Autowired private IDxccEntityRepository       dxccEntityRepository;
	@Autowired private IRelQsoConteoRepository     relQsoConteoRepository;
	@Autowired private ICatBandRepository          catBandRepository;
	
	private DxccEntity mexicoDxccEntity;
	private List<String> allowedMexicoEntities;
	private String patternForNoMexicano = "^\\d+$";
	private List<String> prohibitedWarcBands;
	private List<String> frequencyBandsAllowed;
	
	@PostConstruct private void fillMexicoDxccEntity() {
		this.mexicoDxccEntity = dxccEntityRepository
				.findById(50l)
				.orElse(null);
		
		this.allowedMexicoEntities = Arrays.asList("AGS", "BC", "BCS", "CAM", "CHS", "CHH", "COA", "COL", "CDMX", "EMX",
				"DGO", "GTO", "GRO", "HGO", "JAL", "MIC", "MOR", "NAY", "NL", "OAX", "PUE", "QRO", "QTR", "SLP", "SIN",
				"SON", "TAB", "TMS", "TLX", "VER", "YUC", "ZAC");
		
		prohibitedWarcBands = Arrays.asList("12 meters", "17 meters", "30 meters");
		frequencyBandsAllowed = Arrays.asList("80 meters", "60 meters", "40 meters", "20 meters", "15 meters", "10 meters");
	}
	
	@Override
	public List<CatQsoError> findForErrors(DxccEntity mexicoDxccEntity, Edition edition, ContestLog contestLog, ContestQso qso, List<CatQsoError> qsoErrors) {
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
		
		CatBand band = qso.getBand();
		CatBand qsoBand = null;
		if (band != null) {
			Integer bandId = band.getId();
			qsoBand = catBandRepository.findById(bandId).orElse(null);
			if (!frequencyBandsAllowed.contains(qsoBand.getBand())) {
				listErrors.add(error_QSO_OUT_OF_BAND);
			}
		}
		
		String qsoExchangeEmmited = qso.getExchangee();
		
		DxccEntity contestLogDxccEntity = null;
		if (contestLog.getDxccEntity() != null) {
			Long contestLogDxccEntityId = contestLog.getDxccEntity().getId();
			contestLogDxccEntity = dxccEntityRepository.findByDxccEntityCodeBeforeYear(contestLogDxccEntityId);
		}
		
		if(contestLogDxccEntity != null && contestLogDxccEntity.equals(this.mexicoDxccEntity)) {
			if(!allowedMexicoEntities.contains(qso.getExchangee())) {
				listErrors.add(error_NOT_VALID_EXCHANGE_EMMITED);
			}
		} else {
			if(!qsoExchangeEmmited.matches(patternForNoMexicano)) {
				listErrors.add(error_NOT_VALID_EXCHANGE_EMMITED);
			}
		}
		
		if (qsoBand != null) {
			if (prohibitedWarcBands.contains(qsoBand.getBand())) {
				listErrors.add(error_QSO_ON_WARC_BAND);
			}
		}
		
		
		return listErrors;
	}

	@Override
	public Integer getPoints(DxccEntity mexicoDxccEntity, ContestLog contestLog, ContestQso qso) {
		DxccEntity dxccEntityHome = null;
		if (contestLog.getDxccEntity() != null) {
			Long dxccEntityHomeId = contestLog.getDxccEntity().getId();
			if (dxccEntityHomeId == null)
				return null;
			dxccEntityHome = dxccEntityRepository.findByDxccEntityCodeBeforeYear(dxccEntityHomeId);
		}
		if(dxccEntityHome == null)
			return null;
		
		Long dxccEntityCalledId = null;
		if (qso.getDxccEntity() != null)
			dxccEntityCalledId = qso.getDxccEntity().getId();
		
		if(dxccEntityCalledId == null)
			return null;
		DxccEntity dxccEntityCalled = dxccEntityRepository.findByDxccEntityCodeBeforeYear(dxccEntityCalledId);
		
		if(dxccEntityCalled == null)
			return null;
		
		CatBand qsoBand = qso.getBand();
		if(qsoBand == null)
			return null;
		
		boolean CALLER_IS_MEXICANO = mexicoDxccEntity.equals(dxccEntityHome);
		boolean CALLED_IS_MEXICANO = mexicoDxccEntity.equals(dxccEntityCalled);
		
		int return_val = 3;

		if (CALLER_IS_MEXICANO && CALLED_IS_MEXICANO) {
			return_val = 4;
		}

		return return_val;
	}

	@Override
	public void setMultiplies(List<MultiplierDTO> multiplierList,DxccEntity mexicoDxccEntity, Conteo conteo, List<ContestQso> qsos) {
		List<String> multpliesList = new ArrayList<>();
		List<RelQsoConteo> listRelQsoConteo = new ArrayList<>();
		for(ContestQso qso:qsos) {
			String exchangeR = qso.getExchanger();
			RelQsoConteo relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo);

			if(qso.getDxccEntity() == null) {
				relQsoConteo.setMultiply(false);
				continue;	
			}
			
			DxccEntity qsoDxccEntity = dxccEntityRepository
					.findById(qso.getDxccEntity().getId())
					.orElse(null);
			
			if(qsoDxccEntity.equals(this.mexicoDxccEntity)) {
				if(this.allowedMexicoEntities.contains(exchangeR) && !multpliesList.contains(exchangeR)) {
					relQsoConteo.setMultiply(true);
					multpliesList.add(exchangeR);
				} else {
					relQsoConteo.setMultiply(false);
				}
			} else {
				exchangeR = qso.getDxccEntity().getId() + "";
				if(!multpliesList.contains(exchangeR)) {
					relQsoConteo.setMultiply(true);
					multpliesList.add(exchangeR);
				} else {
					relQsoConteo.setMultiply(false);
				}
			}

			listRelQsoConteo.add(relQsoConteo);
		}
		relQsoConteoRepository.saveAll(listRelQsoConteo);
	}
}