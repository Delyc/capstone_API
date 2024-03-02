package com.househunting.api.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	public String sendTextEmail() throws IOException {
		    Email from = new Email("delyce35@gmail.com");
		    String subject = "The subject";
		    Email to = new Email("delyceu@gmail.co,");
		    Content content = new Content("text/plain", "This is a test email");
		    Mail mail = new Mail(from, subject, to, content);
		
		    SendGrid sg = new SendGrid("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");
		    Request request = new Request();
		    try {
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      logger.info(response.getBody());
		      return response.getBody();	     
		    } catch (IOException ex) {
		      throw ex;
		    }	   
	}
}