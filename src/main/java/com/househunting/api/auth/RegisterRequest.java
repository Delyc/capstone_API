package com.househunting.api.auth;

import com.househunting.api.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String companyName;
    private String tiktok;
    private String youtube;
    private String insta;
    private String phone;
    private String address;
    private Role role;
    private String accountType;
    private String profilePictureUrl;

}