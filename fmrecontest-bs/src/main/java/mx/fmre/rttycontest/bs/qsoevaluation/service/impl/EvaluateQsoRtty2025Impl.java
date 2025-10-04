package mx.fmre.rttycontest.bs.qsoevaluation.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
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

@Service("evaluateQsoRtty2025")
@Slf4j
public class EvaluateQsoRtty2025Impl implements IEvaluateQso {
	
	@Autowired private IDxccEntityRepository       dxccEntityRepository;
	@Autowired private IRelQsoConteoRepository     relQsoConteoRepository;
	@Autowired private ICatBandRepository          catBandRepository;
	
	@Value("${FMRE_CALLSIGN}")
	private String xe1lmCallsign;
	
	private List<String> allowedMexicoEntities;
	private String patternForNoMexicano = "^\\d+$";
	private List<String> prohibitedWarcBands;
	private List<String> frequencyBandsAllowed;
	
	@PostConstruct private void fillMexicoDxccEntity() {
        this.allowedMexicoEntities = Arrays.asList("AGS", "BC", "BCS", "CAM", "CHS", "CHH", "COA", "COL", "CDMX", "CMX",
                "DF", "EMX", "DGO", "GTO", "GRO", "HGO", "JAL", "MIC", "MOR", "NAY", "NL", "NLE", "OAX", "PUE", "QRO",
                "QTR", "SLP", "SIN", "SON", "TAB", "TMS", "TLX", "VER", "YUC", "ZAC");
        
        prohibitedWarcBands = Arrays.asList("12 meters", "17 meters", "30 meters");
        frequencyBandsAllowed = Arrays.asList("80 meters", "60 meters", "40 meters", "20 meters", "15 meters", "10 meters");
	}
	
	@Override
	public List<CatQsoError> findForErrors(DxccEntity mexicoDxccEntity, Edition edition, ContestLog contestLog, ContestQso qso, List<CatQsoError> qsoErrors) {
	    
		List<CatQsoError> listErrors = new ArrayList<>();
		Calendar calendarEditionStartDate = Calendar.getInstance();
		calendarEditionStartDate.setTime(edition.getStart());
		calendarEditionStartDate.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Calendar calendarEditionEndDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendarEditionEndDate.setTime(edition.getEnd());
		calendarEditionEndDate.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Calendar calendarQso = Calendar.getInstance();
		calendarQso.setTime(qso.getDatetime());
		calendarQso.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		CatQsoError error_QSO_MADE_BEFORE_CONTEST_START = qsoErrors
				.stream()
				.filter(e -> "QSO_MADE_BEFORE_CONTEST_START".equals(e.getKey()))
				.findFirst().orElse(null);
		
		CatQsoError error_QSO_MADE_AFTER_CONTEST_END = qsoErrors
				.stream()
				.filter(e -> "QSO_MADE_AFTER_CONTEST_END".equals(e.getKey()))
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
			listErrors.add(error_QSO_MADE_AFTER_CONTEST_END);
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
			Long contestLogDxccEntityId = contestLog.getDxccEntity().getEntityCode();
			contestLogDxccEntity = dxccEntityRepository.findByDxccEntityCodeBeforeYear(contestLogDxccEntityId);
		}
		
		if(contestLogDxccEntity != null && contestLogDxccEntity.equals(mexicoDxccEntity)) {
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
		DxccEntity dxccEntityHome = contestLog.getDxccEntity();
		
		if(dxccEntityHome == null) {
			return null;
		}
		
		if (qso.getDxccEntity() == null) {
			log.warn("No se pudo asignar puntos al QSO id: {} del log id: {} porque no tiene entidad DXCC asignada",
					qso.getId(), contestLog.getId());
			return null;
		}
		
		DxccEntity dxccEntityCalled = dxccEntityRepository.findById(qso.getDxccEntity().getId()).orElse(null);
		
		if(dxccEntityCalled == null) {
			return null;
		}
		
		boolean CALLER_IS_MEXICANO = mexicoDxccEntity.getEntityCode().equals(dxccEntityHome.getEntityCode());
		boolean CALLED_IS_MEXICANO = mexicoDxccEntity.getEntityCode().equals(dxccEntityCalled.getEntityCode());
		
		int return_val = 3;

		if (CALLER_IS_MEXICANO && CALLED_IS_MEXICANO) {
			return_val = 4;
		}

		return return_val;
	}

	@Override
	public void setMultiplies(DxccEntity mexicoDxccEntity, Conteo conteo, List<ContestQso> qsos) {
		//List<String> multpliesList = new ArrayList<>();
		List<RelQsoConteo> listRelQsoConteo = new ArrayList<>();
		for(ContestQso qso:qsos) {
			Integer bandId = qso.getBand() != null ? qso.getBand().getId() : null;
			Long dxccEntityId = qso.getDxccEntity() != null ? qso.getDxccEntity().getId() : null;
			Long satateId = qso.getState() != null ? qso.getState().getId() : null;
			
			MultiplierDTO multiplierDTO = new MultiplierDTO();
			multiplierDTO.setDxccEntityId(dxccEntityId);
			multiplierDTO.setSatateId(satateId);
			multiplierDTO.setBandId(bandId);
			
			List<MultiplierDTO> multiplierList = new ArrayList<>();
			
			//StringBuffer sbKey = new StringBuffer();
			//sbKey.append(qso.getExchanger() != null ? qso.getExchanger() : StaticValues.EMPTY_STRING);
			//sbKey.append(";");
			//sbKey.append(qso.getBand() != null ? qso.getBand().getId() : StaticValues.EMPTY_STRING);
			//String key = sbKey.toString();
			//String exchangeR = qso.getExchanger();
			RelQsoConteo relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(qso, conteo);

			if(qso.getDxccEntity() == null) {
				relQsoConteo.setMultiply(false);
				continue;	
			}
			
			if(qso.getError() != null && qso.getError().booleanValue()) {
				relQsoConteo.setMultiply(false);
                continue;	
            }
			
			if (isMultiplier(multiplierList, mexicoDxccEntity, multiplierDTO)) {
				relQsoConteo.setMultiply(true);
			} else {
				relQsoConteo.setMultiply(false);
			}
			
			/*
			
			DxccEntity qsoDxccEntity = dxccEntityRepository
					.findById(qso.getDxccEntity().getId())
					.orElse(null);
			
			if (qsoDxccEntity.equals(mexicoDxccEntity)) {
				if (this.allowedMexicoEntities.contains(exchangeR) && !multpliesList.contains(key)) {
					relQsoConteo.setMultiply(true);
					multpliesList.add(key);
				} else {
					relQsoConteo.setMultiply(false);
				}
			} else {
				if (!multpliesList.contains(key)) {
					relQsoConteo.setMultiply(true);
					multpliesList.add(key);
				} else {
					relQsoConteo.setMultiply(false);
				}
			}
			*/

			listRelQsoConteo.add(relQsoConteo);
		}
		relQsoConteoRepository.saveAll(listRelQsoConteo);
	}
	
	private boolean isMultiplier(List<MultiplierDTO> multiplierList, DxccEntity mexicoDxccEntity,
			MultiplierDTO multiplierDTO) {
		if (multiplierDTO == null || multiplierDTO.getDxccEntityId() == null || multiplierDTO.getBandId() == null) {
			return false;
		}
		if (multiplierDTO.getDxccEntityId().equals(mexicoDxccEntity.getId())) {
			if (multiplierDTO.getSatateId() == null) {
				return false;
			}
			MultiplierDTO filtered = multiplierList.stream().filter(m -> m.getBandId().equals(multiplierDTO.getBandId())
					&& m.getSatateId().equals(multiplierDTO.getSatateId())).findFirst().orElse(null);
			if (filtered == null) {
				multiplierList.add(multiplierDTO);
				return true;
			}
		} else {
			MultiplierDTO filtered = multiplierList.stream()
					.filter(m -> m.getBandId().equals(multiplierDTO.getBandId())
							&& m.getDxccEntityId().equals(multiplierDTO.getDxccEntityId()))
					.findFirst().orElse(null);
			if (filtered == null) {
				multiplierList.add(multiplierDTO);
				return true;
			}
		}
		return false;
	}
}