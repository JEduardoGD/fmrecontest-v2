package mx.fmre.rttycontest.persistence.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mx.fmre.rttycontest.persistence.model.Contest;

public interface IContestServiceAPI {
	public Page<Contest> getAllPaged(Pageable pageable);
	public List<Contest> getAll();
}
