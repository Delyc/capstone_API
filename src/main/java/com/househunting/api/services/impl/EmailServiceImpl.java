package com.househunting.api.services.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.househunting.api.services.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // @Override
    // public void sendSimpleEmail(String to, String subject, String content) {
    // SimpleMailMessage message = new SimpleMailMessage();
    // message.setTo(to);
    // message.setSubject(subject);
    // message.setText(content);

    // javaMailSender.send(message);
    // }

    @Override
    public void sendSimpleEmail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // Enable HTML content

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

}
