package mx.fmre.rttycontest.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mx.fmre.rttycontest.persistence.model.Contest;
import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.web.helper.ContestHelper;
import mx.fmre.rttycontest.web.helper.EditionHelper;
import mx.fmre.rttycontest.web.service.IContestService;
import mx.fmre.rttycontest.web.service.IEditionService;

@Controller
@RequestMapping(value = "/contest")
public class ContestController {

	@Value("${contest.pagination.rowsperpage}")
	private int rowsPerPage;

	@Autowired private ContestHelper contestHelper;
	@Autowired private IContestService contestService;
	@Autowired private IEditionService editionService;
	@Autowired private EditionHelper editionHelper;

//	@GetMapping
//	public String findAllPaged(@RequestParam Map<String, Object> params, Model model) {
//		int page = params.get("page") != null ? Integer.valueOf(params.get("page").toString()) - 1 : 0;
//		PageListDTO pageListDTO = contestService.getPagedRequest(page);
//		model.addAttribute("pages", pageListDTO.getPages());
//		model.addAttribute("list", pageListDTO.getListContest());
//		return "/contest/contest";
//	}

	@GetMapping
	public String getContestList(@RequestParam Map<String, Object> params, Model model) {
		List<Contest> listContest = contestService.getAll();
		model.addAttribute("listContest", contestHelper.parse(listContest));
		return "contest/contest";
	}

	@GetMapping("/{idContest}")
	public String getEditionsList(@PathVariable("idContest") Integer contestId, Model model) {
		List<Edition> listEdition = editionService.getAllByContestId(contestId);
		model.addAttribute("listEdition", editionHelper.parse(listEdition));
		return "editions/editions";
	}
}
