package com.househunting.api.services;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String content);
}
