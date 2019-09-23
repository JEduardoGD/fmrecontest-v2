package mx.fmre.rttycontest.persistence.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.repository.IEditionRepository;
import mx.fmre.rttycontest.persistence.service.IEditionServiceAPI;

@Service
public class EditionServiceAPIImpl implements IEditionServiceAPI {
	@Autowired
	private IEditionRepository editionRepository;

	@Override
	public List<Edition> getAll() {
		return editionRepository.findAll();
	}

	@Override
	public List<Edition> findByContestId(Integer contestId) {
		return editionRepository.getByContestId(contestId);
	}

	@Override
	public List<Edition> getActiveEditions() {
		return editionRepository.getActiveEditionOfContest();
	}
}
