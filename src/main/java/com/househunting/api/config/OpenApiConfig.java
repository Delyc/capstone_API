package com.househunting.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
            contact = @Contact(
                name = "Delyce",
                email = "delyce35@gmail.com"
            ),
            description = "House hunting API documentation",
            title = "House hunting API",
            version = "1.0.0"
        ),
        servers = {
            @Server(
                description = "Local server",
                url = "http://localhost:8080"
            ),
            @Server(
                description = "Heroku server",
                url = "https://house-hunting-api.herokuapp.com"
            )
        }
)

public class OpenApiConfig {
    
}
