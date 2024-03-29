package com.househunting.api.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.househunting.api.config.JwtService;
import com.househunting.api.exceptions.DuplicateEmailException;
import com.househunting.api.services.MailSenderService;
import com.househunting.api.services.impl.EmailServiceImpl;
import com.househunting.api.user.Role;
import com.househunting.api.user.Status;
import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Cloudinary cloudinary;
    private final EmailServiceImpl emailService;
    


    public AuthenticationResponse register(RegisterRequest request) {
        System.out.println("testing userrrrr ################################################################################################################");

        var existingUser = repository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new DuplicateEmailException("Email already exists");
        }
    
        try {
            // String profilePictureUrl = null;
    
            // if (file != null && !file.isEmpty()) {
            //     var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            //     profilePictureUrl = (String) uploadResult.get("secure_url");
            // } else {

            //     profilePictureUrl = "https://res.cloudinary.com/ddlrtqeqm/image/upload/v1704103066/cld-sample-5.jpg";
            // }

User user = new User();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setStatus(Status.ONLINE);
    user.setAccountType(request.getAccountType());
    user.setAddress(request.getAddress());
    user.setPhone(request.getPhone());
    user.setRole(Role.ADMIN);
    user.setProfilePictureUrl(request.getProfilePictureUrl());
    user.setCompanyName(request.getCompanyName());
    user.setTiktok(request.getTiktok());
    user.setYoutube(request.getYoutube());
    user.setInsta(request.getInsta());
            // var user = User.builder()
            //         .firstName(request.getFirstName())
            //         .status(Status.ONLINE)
            //         .lastName(request.getLastName())
            //         .email(request.getEmail())
            //         .password(passwordEncoder.encode(request.getPassword()))
            //         .accountType(request.getAccountType())
            //         .role(Role.ADMIN)
            //         .address(request.getAddress())
            //         .phone(request.getPhone())
            //         .profilePictureUrl(request.getProfilePictureUrl())
            //         .build();
    

                    System.out.println("testing userrrrr ################################################################################################################" + user);
            repository.save(user);
            System.out.println("testing userrrrr ################################################################################################################" + user);
            
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to register user with profile picture");
        }
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    // public AuthenticationResponse authenticate(AuthenticationRequest request) {
    //     authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    //     var user = repository.findByEmail(request.getEmail())
    //             .orElseThrow(() -> new RuntimeException("User not found"));
    //     var jwtToken = jwtService.generateToken(user);
    //     return AuthenticationResponse.builder()
    //             .token(jwtToken)
    //             .build();
    // }

    public void sendPasswordResetEmail(String jsonString) throws MessagingException, javax.mail.MessagingException {
        // Extract email from JSON
        String email = extractEmail(jsonString);

        if (email != null) {
            System.out.println("Sending password reset email to: " + email);

            // Generate a unique token
            String resetToken = jwtService.generateResetToken(email);

            // Craft an email containing the password reset link with the token
            String resetLink = "http://localhost:8080/reset-password?token=" + resetToken;
            String emailContent = "<p>Click the link to reset your password: <a href='" + resetLink + "'>" + resetLink
                    + "</a></p>";

            // Send email with the password reset link
            // mailSenderService.sendNewMail(email, "Password Reset", emailContent);
        } else {
            System.out.println("No valid email found in the JSON.");
            // Handle the case where no valid email is found in the JSON
        }
    }

    private String extractEmail(String jsonString) {
        // Extract email from JSON
        // Assuming using a library like Gson
        // You might need to adjust this based on how you extract the email from JSON
        // Here's a simplified version assuming the JSON structure is consistent
        // Do proper error handling and JSON parsing based on your context
        try {
            JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
            if (jsonObject.has("email")) {
                return jsonObject.get("email").getAsString().trim();
            } else {
                return null;
            }
        } catch (JsonSyntaxException | IllegalStateException e) {
            // Handle JSON parsing exceptions
            e.printStackTrace();
            return null;
        }
    }


    public void disconnect(User user, Long id){
        var storedUser = repository.findById(id)
        .orElse(null);

        if (storedUser != null){
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }

    }

    public List<User> findConnectedUser(){
        return repository.findAllByStatus(Status.ONLINE);
    }

}




// SPRING_DATASOURCE_URL=jdbc:postgres://admin:xd6ey8lILvkuz6klQpZtpwUULJIDHAAs@dpg-cncsbtun7f5s73bigf60-a.oregon-postgres.render.com/cozyapi
// SPRING_DATASOURCE_USERNAME=admin
// SPRING_DATASOURCE_PASSWORD=xd6ey8lILvkuz6klQpZtpwUULJIDHAAs
// SPRING_MAIL_USERNAME=delyce35@gmail.com
// SPRING_MAIL_PASSWORD=wtkn rmqr idjn lfnv