package mx.fmre.rttycontest.persistence.service;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.Edition;

public interface IEditionServiceAPI {

	public List<Edition> getAll();

	public List<Edition> findByContestId(Integer contestId);

	public List<Edition> getActiveEditions();

}
