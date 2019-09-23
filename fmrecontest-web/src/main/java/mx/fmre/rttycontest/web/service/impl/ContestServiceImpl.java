package mx.fmre.rttycontest.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.service.IContestServiceAPI;
import mx.fmre.rttycontest.web.dto.PageListDTO;
import mx.fmre.rttycontest.web.service.IContestService;

@Service
public class ContestServiceImpl implements IContestService {

	@Value("${contest.pagination.rowsperpage}")
	private int rowsPerPage;

	@Autowired private IContestServiceAPI contestServiceAPI;

	@Override
	public PageListDTO getPagedRequest(Integer pageParam) {
		PageListDTO pageListDTO = new PageListDTO();
		int page = pageParam != null ? pageParam.intValue() - 1 : 0;
		PageRequest pageRequest = PageRequest.of(page, rowsPerPage);
		Page<Contest> pageContest = contestServiceAPI.getAllPaged(pageRequest);
		int totalPages = pageContest.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pages = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			pageListDTO.setPages(pages);
		}
		pageListDTO.setListContest(pageContest.getContent());
		return pageListDTO;
	}

	@Override
	public List<Contest> getAll() {
		return contestServiceAPI.getAll();
	}
}
