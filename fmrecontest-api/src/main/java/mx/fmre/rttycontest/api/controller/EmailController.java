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
import mx.fmre.rttycontest.api.service.IEmailService;
import mx.fmre.rttycontest.exception.FmreContestException;

@RestController
@RequestMapping("/emails")
@Slf4j
public class EmailController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6653984309463596366L;
	
	@Autowired private IEmailService emailService;

	@GetMapping("/byeditionid/{editionid}")
	public ResponseEntity<StdResponse> getAllByContestId(@PathVariable("editionid") Integer editionid) {
		try {
			getResponseServiceVo().setData(emailService.getAllByEdition(editionid));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/subject/{subject}/idedition/{idEdition}")
	public ResponseEntity<StdResponse> getAllBySubjectAndEdition(
			@PathVariable("subject") String subject,
			@PathVariable("idEdition") Integer idEdition) {
		try {
			getResponseServiceVo().setData(emailService.getAllBySubjectAndEditionID(subject, idEdition));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/errors/byeditionid/{idEdition}")
	public ResponseEntity<StdResponse> getErrorsByEdition(@PathVariable("idEdition") Integer idEdition) {
		try {
			getResponseServiceVo().setData(emailService.getOnlyErrores(idEdition));
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
		} catch (FmreContestException e) {
			log.error("{}", e.getLocalizedMessage());
			getResponseServiceVo().setMessageResponse(e.getLocalizedMessage());
			return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
