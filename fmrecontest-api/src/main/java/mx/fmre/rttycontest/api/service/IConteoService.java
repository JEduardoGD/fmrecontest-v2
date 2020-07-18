package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.api.dto.ConteoDto;

public interface IConteoService {
	public List<ConteoDto> getAll();
	public ConteoDto findById(Integer conteoId);
}
