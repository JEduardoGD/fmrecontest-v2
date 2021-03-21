package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.api.dto.QsoDto;
import mx.fmre.rttycontest.api.service.IQsoServie;
import mx.fmre.rttycontest.api.util.QsoUtil;
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
@Slf4j
public class QsoServiceImpl implements IQsoServie {

	@Autowired private IConteoRepository conteoRepository;
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
						.filter(y -> y.getId().equals(qsoBandId))
						.findFirst()
						.orElse(null);
			}
			
			return QsoUtil.map(dxccEntity, relQsoConteo, qsoBand, x);
		}).collect(Collectors.toList());
	}

	@Override
	public QsoDto findById(Integer conteoId, Long qsoId) throws FmreContestException {
		ContestQso contestQso = contestQsoRepository.findById(qsoId).orElse(null);
		if(contestQso == null) {
			log.error("Qso not found with id {}", qsoId);
			throw new FmreContestException("Qso not found");
		}
		
		Conteo conteo = conteoRepository.findById(conteoId).orElse(null);
		
		DxccEntity dxccEntity = null;
		
		Long dxccEntityId = contestQso.getDxccEntity() != null ? contestQso.getDxccEntity().getId() : null;
		
		if(dxccEntityId != null)
			dxccEntity = this.listDxccEntities
					.stream()
					.filter(y -> y.getId().longValue() == dxccEntityId.longValue())
					.findFirst()
					.orElse(null);
		
		RelQsoConteo relQsoConteo = relQsoConteoRepository.findByContestQsoAndConteo(contestQso, conteo);
		
		CatBand qsoBand = null;
		if(contestQso.getBand() != null) {
			Integer qsoBandId = contestQso.getBand().getId();
			qsoBand = listBands
					.stream()
					.filter(y -> y.getId().equals(qsoBandId))
					.findFirst()
					.orElse(null);
		}
			
		return QsoUtil.map(dxccEntity, relQsoConteo, qsoBand, contestQso);
	}

	@Override
	public QsoDto update(QsoDto qsoDto) throws FmreContestException {
		if(qsoDto.isSelectedUpdateAll()) {
			ContestQso qso = contestQsoRepository.findById(qsoDto.getId()).orElse(null);
			ContestLog contestLog = contestLogRepository
					.findById(qso.getContestLog().getId())
					.orElse(null);
			List<ContestQso> contestQsos = contestQsoRepository.findByContestLog(contestLog);
			Integer qsoBandId = qsoDto.getQsoBandId();
			CatBand qsoBand = this.listBands
					.stream()
					.filter(band -> band.getId().intValue() == qsoBandId.intValue())
					.findFirst()
					.orElse(null);
			int frequency = qso.getFrequency();
			List<ContestQso> qsosWithSameFrequency = contestQsos
					.stream()
					.filter(q -> q.getFrequency() == frequency)
					.collect(Collectors.toList());
			List<ContestQso> mappedQsos = qsosWithSameFrequency
					.stream()
					.map(q -> {
						q.setBand(qsoBand);
						return q;
					})
					.collect(Collectors.toList());
			contestQsoRepository.saveAll(mappedQsos);
			return this.findById(qsoDto.getConteoId(), qso.getId());
		}
		
		ContestQso qso = contestQsoRepository.findById(qsoDto.getId()).orElse(null);
		
		Long dxccEntityId = qsoDto.getDxccEntityId();
		DxccEntity dxccEntity = null;
		if (dxccEntityId != null) {
			dxccEntity = dxccEntityRepository
					.findById(dxccEntityId)
					.orElse(null);
			qso.setDxccEntity(dxccEntity);
			qso.setDxccNotFound(false);
		}
		Integer qsoBandId = qsoDto.getQsoBandId();
		if (qsoBandId != null) {
			CatBand qsoBand = this.listBands
					.stream()
					.filter(band -> band.getId().intValue() == qsoBandId.intValue())
					.findFirst()
					.orElse(null);
			qso.setBand(qsoBand);
		}
		if(qsoDto.isDxccOrBandError()) {
			qso.setError(true);
		}
		
		ContestQso newQso = contestQsoRepository.save(qso);
		return this.findById(qsoDto.getConteoId(), newQso.getId());
	}
}













