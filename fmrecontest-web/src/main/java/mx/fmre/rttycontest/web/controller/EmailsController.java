package mx.fmre.rttycontest.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.fmre.rttycontest.persistence.model.Email;
import mx.fmre.rttycontest.persistence.service.IEmailServiceAPI;
import mx.fmre.rttycontest.web.helper.EmailHelper;

@Controller
@RequestMapping(value = "/email")
public class EmailsController {
	
	@Autowired private IEmailServiceAPI emailServiceAPI;
	
	@GetMapping("/by-editionid/{editionId}")
	public String getEditionsList(@PathVariable("editionId") Integer editionId, Model model) {
		List<Email> listEmail = emailServiceAPI.getByEditionId(editionId);
		model.addAttribute("listEmail", EmailHelper.parse(listEmail));
		return "email/email";
	}
}
