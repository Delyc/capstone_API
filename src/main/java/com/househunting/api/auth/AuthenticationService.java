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
import com.househunting.api.services.MailSenderService;
import com.househunting.api.services.impl.EmailServiceImpl;
import com.househunting.api.user.Role;
import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Cloudinary cloudinary;

    private final EmailServiceImpl emailService;
    private final MailSenderService mailSenderService;

    public AuthenticationResponse register(RegisterRequest request, MultipartFile file) {

        var existingUser = repository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new EntityExistsException("Email already exists");
        }

        try {
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String profilePictureUrl = (String) uploadResult.get("secure_url");

            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .address(request.getAddress())
                    .phone(request.getPhone())
                    .profilePictureUrl(profilePictureUrl)
                    .build();

            repository.save(user);
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
            mailSenderService.sendNewMail(email, "Password Reset", emailContent);
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

    // public void sendPasswordResetEmail(String email) {
    // // Assuming you're retrieving the email address from a JSON object
    // String sendTo = email.trim();

    // System.out.println("Sending password reset email to:
    // ########################################" + sendTo);

    // // Generate a unique token
    // String resetToken = jwtService.generateResetToken(email);

    // // Craft an email containing the password reset link with the token
    // String resetLink = "http://localhost:8080/reset-password?token=" +
    // resetToken;
    // String emailContent = "<p>Click the link to reset your password: <a href='" +
    // resetLink + "'>" + resetLink
    // + "</a></p>";

    // // Send email with the password reset link
    // emailService.sendSimpleEmail(sendTo, "Password Reset", emailContent);
    // }

}

// public void sendPasswordResetEmail(String email) {
// // Assuming you're retrieving the email address from a JSON object

// String plainTextEmailContent = convertJsonToPlainText(email);

// //
// String resetToken = jwtService.generateResetToken(plainTextEmailContent);

// // Craft an HTML-formatted email containing the password reset link with the
// // token
// String resetLink = "http://localhost:8080/reset-password?token=" +
// resetToken;
// String emailContent = "<html><body><p>Click the link to reset your password:
// <a href='" + resetLink + "'>"
// + resetLink
// + "</a></p></body></html>";

// // Send HTML-formatted email with the password reset link
// emailService.sendSimpleEmail(plainTextEmailContent, "Password Reset",
// emailContent);
// }