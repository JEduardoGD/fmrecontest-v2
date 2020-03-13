package mx.fmre.rttycontest.api.service;

import java.util.List;

import mx.fmre.rttycontest.dto.EditionDTO;

public interface IEditionService {
	public List<EditionDTO> getAllByContestId(Integer contestId);
	public List<EditionDTO> getAll();
}
