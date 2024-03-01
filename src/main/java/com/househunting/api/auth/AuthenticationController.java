package com.househunting.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.househunting.api.publisher.RabbitMQProducer;
import com.househunting.api.services.MailSenderService;
import com.househunting.api.user.Role;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final MailSenderService mailSenderService;

    @Autowired
    private RabbitMQProducer producer;



    @MessageMapping("/user.register")
    @SendTo("/user/topic")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody  RegisterRequest request){
        RegisterRequest userRequest = new RegisterRequest();
        userRequest.setFirstName(request.getFirstName());
        userRequest.setLastName(request.getLastName());
        userRequest.setEmail(request.getEmail());
        userRequest.setPassword(request.getPassword());
        userRequest.setAddress(request.getAddress());
        userRequest.setPhone(request.getPhone());
        userRequest.setAccountType(request.getAccountType());
        userRequest.setProfilePictureUrl(request.getProfilePictureUrl());
        userRequest.setRole(Role.ADMIN);
System.out.println("################################################" + userRequest);
        return ResponseEntity.ok(service.register(userRequest));

    }
        
    // @RequestParam("file") MultipartFile file,
    //         @RequestParam("firstName") String firstName,
    //         @RequestParam("lastName") String lastName,
    //         @RequestParam("email") String email,
    //         @RequestParam("role") String role,
    //         @RequestParam("password") String password,
    //         @RequestParam("address") String address,
    //         @RequestParam("accountType") String accountType,
    //         @RequestParam("phone") String phone) {
    //     RegisterRequest request = new RegisterRequest();
    //     request.setFirstName(firstName);
    //     request.setLastName(lastName);
    //     request.setEmail(email);
    //     request.setPassword(password);
    //     request.setRole(Role.ADMIN);
    //     request.setAddress(address);
    //     request.setPhone(phone);
    //     request.setAccountType(accountType);

    // }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    // @PostMapping("/authenticate")
    // public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
    //     AuthenticationResponse authResponse = service.authenticate(request);
    //     // Assuming the token is part of the AuthenticationResponse
    //     String token = authResponse.getToken();
    //     // Send the token to RabbitMQ
    //     producer.sendMessage(token);
    //     return ResponseEntity.ok(authResponse);
    // }
    
    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
        producer.sendMessage(message);
        return ResponseEntity.ok("message sent to rabbit mq ....");

    }


}