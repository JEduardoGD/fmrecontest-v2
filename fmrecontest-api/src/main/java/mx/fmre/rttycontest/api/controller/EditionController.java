package mx.fmre.rttycontest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.fmre.rttycontest.api.common.BaseController;
import mx.fmre.rttycontest.api.common.StdResponse;
import mx.fmre.rttycontest.api.service.IEditionService;

@RestController
@RequestMapping("/edition")
public class EditionController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 111790517521089930L;
	
	@Autowired private IEditionService editionService;

	@GetMapping("/bycontestid/{contestid}")
	public ResponseEntity<StdResponse> getAllByContestId(@PathVariable("contestid") Integer contestId) {
		getResponseServiceVo().setData(editionService.getAllByContestId(contestId));
		return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<StdResponse> getAll() {
		getResponseServiceVo().setData(editionService.getAll());
		return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
	}

}
