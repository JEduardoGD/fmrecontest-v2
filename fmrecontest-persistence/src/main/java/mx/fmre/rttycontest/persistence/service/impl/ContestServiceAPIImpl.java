package mx.fmre.rttycontest.persistence.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.repository.IContestRepository;
import mx.fmre.rttycontest.persistence.service.IContestServiceAPI;

@Service
public class ContestServiceAPIImpl implements IContestServiceAPI {

	@Autowired
	private IContestRepository contestRepository;

	@Override
	public Page<Contest> getAllPaged(Pageable pageable) {
		return contestRepository.findAll(pageable);
	}

	@Override
	public List<Contest> getAll() {
		return contestRepository.findAll();
	}

}
