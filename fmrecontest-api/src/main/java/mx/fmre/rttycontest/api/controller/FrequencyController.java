package mx.fmre.rttycontest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.fmre.rttycontest.api.common.BaseController;
import mx.fmre.rttycontest.api.common.StdResponse;
import mx.fmre.rttycontest.api.service.IFrequencyApiService;
import mx.fmre.rttycontest.exception.FmreContestException;

@RestController
@RequestMapping("/frequency")
@Slf4j
public class FrequencyController extends BaseController {/**
	 * 
	 */
	private static final long serialVersionUID = -718077169311161313L;
	
	@Autowired private IFrequencyApiService frequencyService;

	@GetMapping
	public ResponseEntity<StdResponse> getAll() {
		try {
			getResponseServiceVo().setData(frequencyService.getAll());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
