package com.househunting.api.dto;

import com.househunting.api.user.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class UserRequest {
    
    @NotNull(message = "First name cannot be null")
     private String firstName;;

    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    // @Pattern custom validations
    private String password;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;
}
