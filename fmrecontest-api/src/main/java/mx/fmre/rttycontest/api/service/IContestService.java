package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.api.dto.ContestDTO;

public interface IContestService {
	public List<ContestDTO> getAll();
}
