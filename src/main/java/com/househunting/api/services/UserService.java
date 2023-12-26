package com.househunting.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository; // Assuming UserRepository exists

    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null); // Return null or handle the Optional as needed
    }
    // Other user-related methods (create, update, delete, etc.) could be defined here
}

