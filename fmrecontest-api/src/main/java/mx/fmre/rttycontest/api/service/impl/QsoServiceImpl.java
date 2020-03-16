package mx.fmre.rttycontest.api.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.api.dto.QsoDto;
import mx.fmre.rttycontest.api.service.IQsoServie;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.CatBand;
import mx.fmre.rttycontest.persistence.model.Conteo;
import mx.fmre.rttycontest.persistence.model.ContestLog;
import mx.fmre.rttycontest.persistence.model.ContestQso;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.model.RelQsoConteo;
import mx.fmre.rttycontest.persistence.repository.ICatBandRepository;
import mx.fmre.rttycontest.persistence.repository.IConteoRepository;
import mx.fmre.rttycontest.persistence.repository.IContestLogRepository;
import mx.fmre.rttycontest.persistence.repository.IContestQsoRepository;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;
import mx.fmre.rttycontest.persistence.repository.IRelQsoConteoRepository;

@Service
public class QsoServiceImpl implements IQsoServie {

	@Autowired private IConteoRepository conteoRepository;
	@Autowired private IContestLogRepository contestLogRepository;
	@Autowired private IContestQsoRepository contestQsoRepository;
	@Autowired private IRelQsoConteoRepository relQsoConteoRepository;
	@Autowired private IDxccEntityRepository dxccEntityRepository;
	@Autowired private ICatBandRepository catBandRepository;
	
	private List<DxccEntity> listDxccEntities;
	private List<CatBand> listBands;
	
	private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@PostConstruct
	private void initData() {
		this.listDxccEntities = dxccEntityRepository.findAll();
		
		this.listBands = catBandRepository.findAll();
	}
	

	
	@Override
	public List<QsoDto> getQsosByConteoIdAndLogId(int conteoId, long logId) throws FmreContestException {
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
			qsoDto.setDateTime(df.format(x.getDatetime()));
			qsoDto.setExchangeE(x.getExchangee());
			qsoDto.setExchangeR(x.getExchanger());
			qsoDto.setRstE(x.getRste());
			qsoDto.setRstR(x.getRstr());
			qsoDto.setDxccEntity(dxccEntity != null ? (String.format("(%d) %s", dxccEntity.getId(), dxccEntity.getEntity())) : "NOT FOUND");
			qsoDto.setPoints(relQsoConteo.getPoints());
			qsoDto.setMultiply(relQsoConteo.isMultiply());
			qsoDto.setQsoBand(qsoBand != null ? qsoBand.getBand() : "NOT FOUND");
			
			return qsoDto;
		}).collect(Collectors.toList());
	}
}
