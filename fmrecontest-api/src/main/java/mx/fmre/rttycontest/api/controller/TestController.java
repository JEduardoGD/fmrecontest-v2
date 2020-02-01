package mx.fmre.rttycontest.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/hello")
public class TestController {
	@GetMapping
	public ResponseEntity<String> hello() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Custom-Header", "foo");
		return new ResponseEntity<>("hello", headers, HttpStatus.OK);
	}
}
