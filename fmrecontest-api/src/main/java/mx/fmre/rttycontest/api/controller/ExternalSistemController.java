package mx.fmre.rttycontest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.fmre.rttycontest.api.common.BaseController;
import mx.fmre.rttycontest.api.common.StdResponse;
import mx.fmre.rttycontest.api.service.ExternalImportService;

@RestController
@RequestMapping("/external")
//@Slf4j
public class ExternalSistemController extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 4593230966751908629L;
    
    @Autowired
    ExternalImportService externalImportService;

    @GetMapping("/import")
    public ResponseEntity<StdResponse> getAllByContestId() {
        getResponseServiceVo().setData(externalImportService.importExternal());
        return new ResponseEntity<StdResponse>(getResponseServiceVo(), HttpStatus.OK);
    }

}
