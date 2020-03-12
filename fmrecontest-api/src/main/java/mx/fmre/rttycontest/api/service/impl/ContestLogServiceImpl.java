package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.dto.ContestlogDto;
import mx.fmre.rttycontest.api.dto.QsoDto;
import mx.fmre.rttycontest.api.service.IContestLogService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.RelConteoContestLog;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.repository.ICatBandRepository;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.IRelConteoContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoRepository;

@Service
public class ContestLogServiceImpl implements IContestLogService {
	@Autowired private IConteoRepository conteoRepository;
	@Autowired private IRelConteoContestLogRepository relConteoContestLogRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IContestQsoRepository contestQsoRepository;
	@Autowired private IRelQsoConteoRepository relQsoConteoRepository;
	@Autowired private IDxccEntityRepository dxccEntityRepository;
	@Autowired private ICatBandRepository catBandRepository;
	
	private List<DxccEntity> listDxccEntities;
	private List<CatBand> listBands;
	
	@PostConstruct
	private void initData() {
		this.listDxccEntities = dxccEntityRepository.findAll();
		
		this.listBands = catBandRepository.findAll();
	}

	@Override
	public List<ContestlogDto> findByConteoId(Integer conteoId) throws FmreContestException {
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		List<RelConteoContestLog> relConteoContestLogs = relConteoContestLogRepository.findByConteo(conteo);
		
		return relConteoContestLogs.stream().map(x -> {
			ContestLog contestLog = contestLogRepository.findById(x.getContestLog().getId())
					.orElse(null);
			
			Integer emailId = contestLog.getEmail().getId();

			List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
			int contestQsoSize = contestQsos.size();
			
			ContestlogDto contestlogDto = new  ContestlogDto();
			contestlogDto.setId(contestLog.getId());
			contestlogDto.setEmailId(emailId);
			contestlogDto.setCallsign(contestLog.getCallsign());
			contestlogDto.setContestQsoSize(contestQsoSize);
			contestlogDto.setSumOfPoints(x.getSumOfPoints());
			contestlogDto.setMultipliers(x.getMultipliers());
			contestlogDto.setTotalPoints(x.getTotalPoints());
			contestlogDto.setComplete(x.isComplete());
			return contestlogDto;
		}).collect(Collectors.toList());
	}
	
	@Override
	public List<QsoDto> getContestlogByConteoIdAndLogId(int conteoId, long logId) throws FmreContestException {
		ContestLog contestLog = contestLogRepository.findById(logId).orElse(null);
		List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
		
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		
		return contestQsos.stream().map(x -> {
			RelQsoConteo relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(x, conteo);
			
			Long dxccEntityId = x.getDxccEntity() != null ? x.getDxccEntity().getId() : null;
			DxccEntity dxccEntity = null;
			if(dxccEntityId != null) {
				dxccEntity = this.listDxccEntities
						.stream()
						.filter(y -> y.getId().longValue() == dxccEntityId.longValue())
						.findFirst()
						.orElse(null);
			}
			
			CatBand qsoBand = null;
			if(x.getBand() != null) {
				Integer qsoBandId = x.getBand().getId();
				qsoBand = listBands
						.stream()
						.filter(y -> y.getId() == qsoBandId)
						.findFirst()
						.orElse(null);
			}
			
			QsoDto qsoDto = new QsoDto();
			qsoDto.setId(x.getId());
			qsoDto.setFrequency(x.getFrequency());
			qsoDto.setCallsignE(x.getCallsigne());
			qsoDto.setCallsignR(x.getCallsignr());
			qsoDto.setDateTime(x.getDatetime());
			qsoDto.setExchangeE(x.getExchangee());
			qsoDto.setExchangeR(x.getExchanger());
			qsoDto.setRstE(x.getRste());
			qsoDto.setRstR(x.getRstr());
			qsoDto.setDxccNotFound(x.getDxccNotFound() ? "NOT FOUND" : "");
			qsoDto.setDxccEntity(dxccEntity != null ? (String.format("(%d) %s", dxccEntity.getId(), dxccEntity.getEntity())) : "");
			qsoDto.setPoints(relQsoConteo.getPoints());
			qsoDto.setMultiply(relQsoConteo.isMultiply());
			qsoDto.setQsoBandNotFound(qsoBand == null ? "NOT FOUND" : "");
			qsoDto.setQsoBand(qsoBand != null ? qsoBand.getBand() : "");
			
			return qsoDto;
		}).collect(Collectors.toList());
	}
}
