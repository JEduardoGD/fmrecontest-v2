package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.api.dto.DxccEntityDTO;
import mx.fmre.rttycontest.exception.FmreContestException;

public interface IDxccEntityService {

	public List<DxccEntityDTO> getAll() throws FmreContestException;

	public DxccEntityDTO findById(Long dxccEntityId) throws FmreContestException;
}
