package com.househunting.api.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.hamdibouallegue.sendgriddemo.model.InvitationEmail;
// import com.hamdibouallegue.sendgriddemo.service.MailService;
// import com.househunting.api.services.MailServiceyes;
import com.househunting.api.services.MailService;


@RestController
@RequestMapping(value = "/api")
public class MailController {

	@Autowired
	MailService mailService;
	
	@PostMapping("/v1/wishlist/share")
	public String send() throws IOException {
		return mailService.sendTextEmail();
	}
	

}