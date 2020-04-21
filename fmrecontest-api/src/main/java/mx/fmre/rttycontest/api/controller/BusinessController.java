package mx.fmre.rttycontest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.api.common.BaseController;
import mx.fmre.rttycontest.api.common.StdResponse;
import mx.fmre.rttycontest.api.dto.ContestlogDto;
import mx.fmre.rttycontest.api.service.IBusinessService;
import mx.fmre.rttycontest.exception.FmreContestException;

@RestController
@RequestMapping("/business")
@Slf4j
public class BusinessController extends BaseController {
	/**
	* 
	*/
	private static final long serialVersionUID = -2084514638618017090L;
	
	@Autowired private IBusinessService businessService;
	
	@PostMapping
	public ResponseEntity<StdResponse> recountLog(@RequestBody ContestlogDto contestlogDto) {
		try {
			getResponseServiceVo().setData(businessService.recalculateLog(contestlogDto));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
