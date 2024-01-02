package com.househunting.api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping(value = "/register", consumes = "multipart/form-data")
public ResponseEntity<AuthenticationResponse> register(
        @RequestParam("file")  MultipartFile file,
        @RequestParam("firstName")  String firstName,
        @RequestParam("lastName")  String lastName,
        @RequestParam("email")  String email,
        @RequestParam("password")  String password,
        @RequestParam("address")  String address,            
        @RequestParam("role") String role,
        @RequestParam("phone")  String phone,

        @Valid RegisterRequest request 

  ) {



   
    return ResponseEntity.ok(service.register(request, file));
}



    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }



}
