package com.househunting.api.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME="ddlrtqeqm";
    private final String API_KEY = "542831786772373";
    private final String API_SECRET = "bnfW8CFA0fDzyDJ-K-SlCWjIh4I";

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary("cloudinary://" + API_KEY + ":" + API_SECRET + "@" + CLOUD_NAME);
    }
}
