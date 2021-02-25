package mx.fmre.rttycontest.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.api.dto.DxccEntityDTO;
import mx.fmre.rttycontest.api.service.IDxccEntityService;
import mx.fmre.rttycontest.exception.FmreContestException;
import mx.fmre.rttycontest.persistence.model.DxccEntity;
import mx.fmre.rttycontest.persistence.repository.IDxccEntityRepository;

@Service
@Slf4j
public class DxccEntityServiceImpl implements IDxccEntityService {

	@Autowired private IDxccEntityRepository dxccEntityRepository;

	@Override
	public List<DxccEntityDTO> getAll() throws FmreContestException {
		List<DxccEntity> dxccEntityList = dxccEntityRepository.findAll();
		return dxccEntityList.stream().map(x -> map(x)).collect(Collectors.toList());
	}

	@Override
	public DxccEntityDTO findById(Long dxccEntityId) throws FmreContestException {
		DxccEntity dxccEntity = this.dxccEntityRepository.findByDxccEntityCodeBeforeYear(dxccEntityId);
		if(dxccEntity == null) {
			log.error("Cannot find dxccEntity with ID {}", dxccEntityId);
			throw new FmreContestException("Cannot find DxccEnttiy");
		}
		return this.map(dxccEntity);
	}

	public DxccEntityDTO map(DxccEntity dxccEntity) {
		DxccEntityDTO dxccEntityDTO = new DxccEntityDTO();
		
		dxccEntityDTO.setId(dxccEntity.getId());
		dxccEntityDTO.setEntity(dxccEntity.getEntity());
		dxccEntityDTO.setCont(dxccEntity.getCont());
		dxccEntityDTO.setItu(dxccEntity.getItu());
		dxccEntityDTO.setCq(dxccEntity.getCq());
		dxccEntityDTO.setOrigen(dxccEntity.getOrigen());
		
		return dxccEntityDTO;
	}

}
