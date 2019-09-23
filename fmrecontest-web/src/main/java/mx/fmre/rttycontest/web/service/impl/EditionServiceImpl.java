package mx.fmre.rttycontest.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.persistence.service.IEditionServiceAPI;
import mx.fmre.rttycontest.web.service.IEditionService;

@Service
public class EditionServiceImpl implements IEditionService {
	
	@Autowired private IEditionServiceAPI editionServiceAPI;

	@Override
	public List<Edition> getAll() {
		return editionServiceAPI.getAll();
	}

	@Override
	public List<Edition> getAllByContestId(Integer contestId) {
		return editionServiceAPI.findByContestId(contestId);
	}

}
