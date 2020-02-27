package mx.fmre.rttycontest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.fmre.rttycontest.api.common.BaseController;
import mx.fmre.rttycontest.api.common.StdResponse;
import mx.fmre.rttycontest.api.service.IConteoService;

@RestController
@RequestMapping("/conteo")
public class ConteoController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7252056366309935909L;
	
	@Autowired private IConteoService conteoService;

	@GetMapping
	public ResponseEntity<StdResponse> getAll() {
		getResponseServiceVo().setData(conteoService.getAll());
		return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
	}
}
