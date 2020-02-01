package mx.fmre.rttycontest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.fmre.rttycontest.api.common.BaseController;
import mx.fmre.rttycontest.api.common.StdResponse;
import mx.fmre.rttycontest.api.service.IContestService;

@RestController
@RequestMapping("/contest")
public class ContestController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4333639451115849458L;
	@Autowired private IContestService contestService;

	@GetMapping
	public ResponseEntity<StdResponse> getAll() {
		getResponseServiceVo().setData(contestService.getAll());
		return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
	}

}
