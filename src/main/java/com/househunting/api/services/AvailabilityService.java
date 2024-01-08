package com.househunting.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.househunting.api.entity.Availability;
import com.househunting.api.repository.AvailabilityRepository;
import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public List<Availability> getAvailabilitiesByUserId(Long userId) {
        return availabilityRepository.findByUserId(userId);
    }

    @Autowired
    UserRepository userRepository;

   public ResponseEntity<Void> saveAvailability(Long user_id, Availability availability) {
        Optional<User> existingUser = userRepository.findById(user_id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            availability.setUser(user);
            availabilityRepository.save(availability);

            // return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            // User does not exist, return a 404 Not Found response
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return null;
    }

}
