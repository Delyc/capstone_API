package com.househunting.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
                description = "Railway server",
                url = "https://capstoneapi-production-b1ec.up.railway.app/"
            )
        },

        security = {
            @SecurityRequirement(
                name = "bearerAuth"
            )
        }

)


@SecurityScheme(
    name="bearerAuth",
    description = "JWT auth description",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
    
}
