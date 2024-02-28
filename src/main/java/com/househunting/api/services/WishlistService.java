package com.househunting.api.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import com.househunting.api.dto.HouseResponse;
import com.househunting.api.dto.WishlistResponse;
import com.househunting.api.entity.House;
import com.househunting.api.entity.Wishlist;
import com.househunting.api.repository.HouseRepository;
import com.househunting.api.repository.WishlistRepository;
import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class WishlistService {

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    UserRepository userRepository;

    private final TemplateEngine thymeleafTemplateEngine;

    @Autowired
    public WishlistService(TemplateEngine thymeleafTemplateEngine) {
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    public void addHouseToWishlist(Long user_id, Long house_id) {
        User user = userRepository.findById(user_id).orElse(null);
        House house = houseRepository.findById(house_id).orElse(null);

        if (user != null && house != null) {
            Wishlist existingWishlistItem = wishlistRepository.findByUserAndHouse(user, house);
            if (existingWishlistItem != null) {
                // Item already exists, remove it
                wishlistRepository.delete(existingWishlistItem);
            } else {
                // Item doesn't exist, add it
                Wishlist wishlist = new Wishlist();
                wishlist.setUser(user);
                wishlist.setHouse(house);
                wishlistRepository.save(wishlist);
            }
        }
    }

    @Transactional
    public void emptyWishlistByUserId(Long user_id) {
        wishlistRepository.deleteByUserId(user_id);
    }

    public List<WishlistResponse> getUserWishlistWithHouseDetails(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<WishlistResponse> wishlistResponses = new ArrayList<>();
    
            for (Wishlist wishlist : user.getWishlists()) {
                WishlistResponse response = new WishlistResponse();
                response.setId(wishlist.getId());
                House house = wishlist.getHouse();
                HouseResponse houseResponse = new HouseResponse();
                houseResponse.setId(house.getId());
                houseResponse.setTitle(house.getTitle());
                houseResponse.setPrice(house.getPrice());
                houseResponse.setCoverImageUrl(house.getCoverImageUrl());
                houseResponse.setDescription(house.getDescription());
                houseResponse.setLongi(house.getLongi());
                houseResponse.setLat(house.getLat());
                houseResponse.setStreetNumber(house.getStreetNumber());
                // houseResponse.setGoogleMapLocation(house.getGoogleMapLocation());
    
                // Retrieve agent information
                User agent = house.getAgent(); // Use the 'getAgent' method
                if (agent != null) {
                    // Set agent-related fields in the response
                    houseResponse.setAgentId(agent.getId());
                    houseResponse.setAgentEmail(agent.getEmail());
                    houseResponse.setAgentPhoneNumber(agent.getPhone());
                    houseResponse.setAgentName(agent.getFirstName()); // Adjust this based on your User entity

                }
    
                response.setHouse(houseResponse);
                wishlistResponses.add(response);
            }
            return wishlistResponses;
        } else {
            return Collections.emptyList();
        }
    }
    
    public void shareWishlist(Long userId, String recipientEmail) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<WishlistResponse> wishlistResponses = new ArrayList<>();

            for (Wishlist wishlist : user.getWishlists()) {
                WishlistResponse response = new WishlistResponse();
                response.setId(wishlist.getId());

                House house = wishlist.getHouse();
                HouseResponse houseResponse = new HouseResponse();
                houseResponse.setId(house.getId());
                houseResponse.setTitle(house.getTitle());
                houseResponse.setPrice(house.getPrice());
                houseResponse.setCoverImageUrl(house.getCoverImageUrl());
                houseResponse.setDescription(house.getDescription());
                // houseResponse.setGoogleMapLocation(house.getGoogleMapLocation());

                response.setHouse(houseResponse);
                wishlistResponses.add(response);
            }
            sendShareableWishlistEmail(recipientEmail, wishlistResponses);
        }
    }
    private void sendShareableWishlistEmail(String recipientEmail, List<WishlistResponse> wishlistResponses) {
        Context context = new Context();
        context.setVariable("wishlistResponses", wishlistResponses);
        // Use Thymeleaf template engine to process the HTML template
        String emailContent = thymeleafTemplateEngine.process("wishlist-template", context);
    
        try {
            mailSenderService.sendNewMail(recipientEmail, "Houses Shared with You", emailContent);
        } catch (MessagingException | javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }


    
}
