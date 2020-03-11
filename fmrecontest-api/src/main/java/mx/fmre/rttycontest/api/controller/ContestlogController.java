package mx.fmre.rttycontest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.api.common.BaseController;
import mx.fmre.rttycontest.api.common.StdResponse;
import mx.fmre.rttycontest.api.service.impl.ContestLogServiceImpl;
import mx.fmre.rttycontest.exception.FmreContestException;

@RestController
@RequestMapping("/contestlog")
@Slf4j
public class ContestlogController extends BaseController {
	
	@Autowired private ContestLogServiceImpl contestLogServiceImpl;

//	@GetMapping("/byconteoid/{conteoId}")
	public ResponseEntity<StdResponse> getAllByConteoId(@PathVariable("conteoId") Integer conteoId) {
		try {
			getResponseServiceVo().setData(contestLogServiceImpl.findByConteoId(conteoId));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
