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
import mx.fmre.rttycontest.api.service.IDxccEntityService;
import mx.fmre.rttycontest.exception.FmreContestException;

@RestController
@RequestMapping("/dxccentity")
@Slf4j
public class DxccentityController extends BaseController{/**
	 * 
	 */
	private static final long serialVersionUID = 7136638953020895040L;
	
	@Autowired private IDxccEntityService dxccEntityService;
	
	@GetMapping
	public ResponseEntity<StdResponse> findAll() {
		try {
			getResponseServiceVo().setData(dxccEntityService.getAll());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/bydxccentityid/{dxccEntityId}")
	public ResponseEntity<StdResponse> findById(@PathVariable("dxccEntityId") Long dxccEntityId) {
		try {
			getResponseServiceVo().setData(dxccEntityService.findById(dxccEntityId));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
