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

import mx.fmre.rttycontest.bs.reports.service.IResultsReports;

@RestController
@RequestMapping("/results")
public class ResultsController {
	@Autowired private IResultsReports resultsReports;
	
	@GetMapping("/highpowerworld/{conteoId}")
	public ResponseEntity<Resource> highPowerWorldReport(@PathVariable("conteoId") Integer conteoId) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray = resultsReports.highPowerWorld(conteoId);
        ByteArrayResource resource = new ByteArrayResource(bytesrray);
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(bytesrray.length)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
	
	@GetMapping("/lowPowerWorld/{conteoId}")
	public ResponseEntity<Resource> lowPowerWorldReport(@PathVariable("conteoId") Integer conteoId) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray = resultsReports.lowPowerWorld(conteoId);
        ByteArrayResource resource = new ByteArrayResource(bytesrray);
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(bytesrray.length)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
	
	@GetMapping("/highPowerMexico/{conteoId}")
	public ResponseEntity<Resource> highPowerMexicoReport(@PathVariable("conteoId") Integer conteoId) {
		HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        byte[] bytesrray = resultsReports.highPowerMexico(conteoId);
        ByteArrayResource resource = new ByteArrayResource(bytesrray);
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(bytesrray.length)
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
}
