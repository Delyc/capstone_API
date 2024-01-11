package com.househunting.api.services;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendNewMail(String to, String wishlistResponses, String body) throws jakarta.mail.MessagingException, MessagingException {
        jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(wishlistResponses);
        helper.setText(body, true); // Set the content type to true for HTML
        mailSender.send(message);
    }
}
