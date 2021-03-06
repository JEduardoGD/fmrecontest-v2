package mx.fmre.rttycontest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import mx.fmre.rttycontest.api.common.BaseController;
import mx.fmre.rttycontest.bs.reports.service.ICsvReportsService;
import mx.fmre.rttycontest.exception.FmreContestException;

@RestController
@RequestMapping("/reports")
public class ReportsController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -613764212672345431L;
	@Autowired private ICsvReportsService csvReportsService;

	@GetMapping("/conteoid/{conteoId}")
	public ResponseEntity<Resource> generateConteoReport(@PathVariable("conteoId") Integer conteoId) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray = csvReportsService.generateConteoReport(conteoId);
        ByteArrayResource resource = new ByteArrayResource(bytesrray);
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(bytesrray.length)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
	
	@GetMapping("/getallemail/{editionId}")
	public ResponseEntity<Resource> getAllByEditionId(@PathVariable("editionId") Integer editionId) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray = csvReportsService.getAllEmailsOnByEditionId(editionId);
        ByteArrayResource resource = new ByteArrayResource(bytesrray);
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(bytesrray.length)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .contentType(new MediaType("text", "csv"))
	            .body(resource);
	}
	
	@GetMapping("/getbylogid/{logId}/conteoid/{conteoId}")
	public ResponseEntity<Resource> getAllByEditionId(
			@PathVariable("conteoId") Integer conteoId,
			@PathVariable("logId") Long logId) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray = csvReportsService.generateLogReport(conteoId, logId);
        ByteArrayResource resource = new ByteArrayResource(bytesrray);
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(bytesrray.length)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
	
	@GetMapping("/callsignwithoutdxccentityreport/{editionId}")
	@ApiOperation(value = "Read all users matching given filter",  notes = "Will get all the users for the given filter")
	public ResponseEntity<Resource> getCallsignWithoutDxccEntityReport(
			@PathVariable("editionId") Integer editionId) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray = csvReportsService.getCallsignWithoutDxccEntityReport(editionId);
        ByteArrayResource resource = new ByteArrayResource(bytesrray);
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(bytesrray.length)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
    
    @GetMapping("/getCallersByEntity/{editionId}")
    public ResponseEntity<ByteArrayResource> getCallersByEntity(
            @PathVariable("editionId") Integer editionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray;
        try {
            bytesrray = csvReportsService.getCallersByEntity(editionId);
            ByteArrayResource resource = new ByteArrayResource(bytesrray);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(bytesrray.length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (FmreContestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @GetMapping("/getCalledByEntity/{editionId}")
    public ResponseEntity<ByteArrayResource> getCalledByEntity(
            @PathVariable("editionId") Integer editionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray;
        try {
            bytesrray = csvReportsService.getCalledByEntity(editionId);
            ByteArrayResource resource = new ByteArrayResource(bytesrray);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(bytesrray.length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (FmreContestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @GetMapping("/emailswitherrors/{editionId}")
    public ResponseEntity<ByteArrayResource> emailsWithErrors(
            @PathVariable("editionId") Integer editionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray;
        try {
            bytesrray = csvReportsService.getEmailsWithErrors(editionId);
            ByteArrayResource resource = new ByteArrayResource(bytesrray);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(bytesrray.length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (FmreContestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
