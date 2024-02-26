package com.househunting.api.demo;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/demo")
@Tag(name = "demo", description = "Demo API")
public class DemoController {

    @Operation(
        summary = "Say hello", 
        description = "Say hello to the world",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Hello World!"
            ),
            @ApiResponse(
                responseCode = "403", 
                description = "Authentication failed"
            )
        }
        )
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World!");
    }
    
    
    
}
