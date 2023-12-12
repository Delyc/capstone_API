package com.househunting.api.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME="dd5ruf2qc";
    private final String API_KEY = "193735459375939";
    private final String API_SECRET = "ihNGaddQqMBzg1GWIXac6uFSths";

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary("cloudinary://" + API_KEY + ":" + API_SECRET + "@" + CLOUD_NAME);
    }
}
