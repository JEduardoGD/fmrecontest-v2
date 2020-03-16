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
import mx.fmre.rttycontest.api.service.IQsoServie;
import mx.fmre.rttycontest.exception.FmreContestException;

@RestController
@RequestMapping("/qsos")
@Slf4j
public class QsosController extends BaseController {/**
	 * 
	 */
	private static final long serialVersionUID = 8747480931394486252L;
	
	@Autowired private IQsoServie qsoServie;

	@GetMapping("/byconteoid/{conteoId}/logid/{logid}")
	public ResponseEntity<StdResponse> getAllByConteoId(
			@PathVariable("conteoId") Integer conteoId,
			@PathVariable("logid") Integer logId) {
		try {
			getResponseServiceVo().setData(qsoServie.getQsosByConteoIdAndLogId(conteoId, logId));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
