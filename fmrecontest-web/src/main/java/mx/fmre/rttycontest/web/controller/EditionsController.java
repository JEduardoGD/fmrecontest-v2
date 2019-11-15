package mx.fmre.rttycontest.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.fmre.rttycontest.persistence.model.Edition;
import mx.fmre.rttycontest.web.helper.EditionHelper;
import mx.fmre.rttycontest.web.service.IEditionService;

@Controller
@RequestMapping(value = "/edition")
public class EditionsController {

	
	@Autowired private IEditionService editionService;
	@Autowired private EditionHelper editionHelper;

	@GetMapping("/bycontestid/{contestId}")
	public String getEditionsList(@PathVariable("contestId") Integer contestId, Model model) {
		List<Edition> listEdition = editionService.getAllByContestId(contestId);
		model.addAttribute("listEdition", editionHelper.parse(listEdition));
		return "editions/editions";
	}
}
