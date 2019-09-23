package mx.fmre.rttycontest.web.service;

import java.util.List;

import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.web.dto.PageListDTO;

public interface IContestService {

	public PageListDTO getPagedRequest(Integer pageParam);

	public List<Contest> getAll();

}
