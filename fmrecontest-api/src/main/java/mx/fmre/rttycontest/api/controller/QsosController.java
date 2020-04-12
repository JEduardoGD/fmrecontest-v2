package mx.fmre.rttycontest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.api.common.BaseController;
import mx.fmre.rttycontest.api.common.StdResponse;
import mx.fmre.rttycontest.api.dto.QsoDto;
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

	@GetMapping("/byid/{qsoId}/andconteoid/{conteoid}")
	public ResponseEntity<StdResponse> findById(
			@PathVariable("conteoid") Integer conteoId,
			@PathVariable("qsoId") Long qsoId) {
		try {
			getResponseServiceVo().setData(qsoServie.findById(conteoId, qsoId));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<StdResponse> updateQso(@RequestBody QsoDto qsoDto) {
		try {
			getResponseServiceVo().setData(qsoServie.update(qsoDto));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	Long contestLogId, Integer conteoId, Integer editionId

	@GetMapping("/contestlogid/{contestLogId}/conteoid/{conteoId}/editionid/{editionId}")
	public ResponseEntity<StdResponse> reprocessLog(
			@PathVariable("conteoid") Long contestLogId,
			@PathVariable("qsoId") Integer conteoId,
			@PathVariable("qsoId") Integer editionId) {
		try {
			getResponseServiceVo().setData(qsoServie.reevaluateLog(contestLogId, conteoId, editionId));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
