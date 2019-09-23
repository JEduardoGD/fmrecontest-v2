package mx.fmre.rttycontest.web.service;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.Edition;

public interface IEditionService {

	public List<Edition> getAll();
	public List<Edition> getAllByContestId(Integer contestId);

}
