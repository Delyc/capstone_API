package com.househunting.api.services;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.househunting.api.dto.HouseRequest;
import com.househunting.api.dto.HouseResponse;
import com.househunting.api.dto.WishlistResponse;
import com.househunting.api.entity.House;
import com.househunting.api.entity.Wishlist;
import com.househunting.api.repository.HouseRepository;
import com.househunting.api.user.User;
import com.househunting.api.user.UserRepository;

@Service
public class HouseService {

    @Autowired 
    Cloudinary cloudinary;
    @Autowired 
    HouseRepository houseRepository;
    @Autowired
    UserRepository userRepository;
    
    public House createHouse(HouseRequest request, MultipartFile file) {
        try {
            // Upload file to Cloudinary and get the secure URL
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String coverImageUrl = (String) uploadResult.get("secure_url");
    
            // Use the userRepository to fetch the User entity by ID
            Optional<User> userOptional = userRepository.findById(request.getUserId());
    
            // Unwrap the Optional or handle the case where the user is not found
            User user = userOptional.orElseThrow(() -> new RuntimeException("User not found for the given ID"));
    
            // Create a new House entity
            House house = House.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .coverImageUrl(coverImageUrl)
                    .price(request.getPrice())
                    .googleMapLocation(request.getGoogleMapLocation())
                    .agent(user)  // Set the user as the agent for the house
                    .build();
    
            // Save the new House entity to the database
            houseRepository.save(house);
    
            return house;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create house with cover image");
        }
    }
    
    public List<HouseResponse> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        List<HouseResponse> houseResponses = new ArrayList<>();
    
        for (House house : houses) {
            HouseResponse houseResponse = new HouseResponse();
            houseResponse.setId(house.getId());
            houseResponse.setTitle(house.getTitle());
            houseResponse.setPrice(house.getPrice());
            houseResponse.setCoverImageUrl(house.getCoverImageUrl());
            houseResponse.setDescription(house.getDescription());
            houseResponse.setGoogleMapLocation(house.getGoogleMapLocation());
    
            // Retrieve agent information
            User agent = house.getAgent(); // Use the 'getAgent' method
            if (agent != null) {
                // Set agent-related fields in the response\
                houseResponse.setAgentId(agent.getId());
                // houseResponse.setAgentId(agent.getId());
                // houseResponse.se
                houseResponse.setAgentEmail(agent.getEmail());
                houseResponse.setAgentPhonenumber(agent.getPhone());
                houseResponse.setAgentName(agent.getFirstName()); // Adjust this based on your User entity
                // Add other agent-related fields as needed
            }
    
            // Include wishlist information
            List<WishlistResponse> wishlistResponses = new ArrayList<>();
            for (Wishlist wishlist : house.getWishlists()) {
                WishlistResponse wishlistResponse = new WishlistResponse();
                wishlistResponse.setId(wishlist.getId());
                wishlistResponse.setId(wishlist.getUser().getId()); // Set user ID
                // Set other wishlist-related fields
                wishlistResponses.add(wishlistResponse);
            }
            houseResponse.setWishlists(wishlistResponses);
    
            houseResponses.add(houseResponse);
        }
    
        return houseResponses;
    }
    
    public HouseResponse getHouseById(Long houseId) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
    
        if (houseOptional.isPresent()) {
            House house = houseOptional.get();
            HouseResponse houseResponse = new HouseResponse();
            houseResponse.setId(house.getId());
            houseResponse.setTitle(house.getTitle());
            houseResponse.setPrice(house.getPrice());
            houseResponse.setCoverImageUrl(house.getCoverImageUrl());
            houseResponse.setDescription(house.getDescription());
            houseResponse.setGoogleMapLocation(house.getGoogleMapLocation());
    
            // Retrieve agent information
            User agent = house.getAgent(); // Use the 'getAgent' method
            if (agent != null) {
                // Set agent-related fields in the response
                houseResponse.setAgentId(agent.getId());
                houseResponse.setAgentEmail(agent.getEmail());
                houseResponse.setAgentPhonenumber(agent.getPhone());
                houseResponse.setAgentName(agent.getFirstName()); // Adjust this based on your User entity
                // Add other agent-related fields as needed
            }
    
            // Include wishlist information
            List<WishlistResponse> wishlistResponses = new ArrayList<>();
            for (Wishlist wishlist : house.getWishlists()) {
                WishlistResponse wishlistResponse = new WishlistResponse();
                wishlistResponse.setId(wishlist.getId());
                wishlistResponse.setId(wishlist.getUser().getId()); // Set user ID
                // Set other wishlist-related fields
                wishlistResponses.add(wishlistResponse);
            }
            houseResponse.setWishlists(wishlistResponses);
    
            return houseResponse;
        } else {
            // Handle house not found
            return null;
        }
    }
    
    public void deleteHouseById(Long id) {
        House houseToDelete = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found with ID: " + id));
        
        houseRepository.delete(houseToDelete);
    }


    public House updateHouse(Long id, MultipartFile file, String title, String description, String price,
                         String googleMapLocation) {
    House houseToUpdate = houseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("House not found with ID: " + id));

    if (title != null && !title.isEmpty()) {
        houseToUpdate.setTitle(title);
    }

    if (description != null && !description.isEmpty()) {
        houseToUpdate.setDescription(description);
    }

    if (price != null && !price.isEmpty()) {
        houseToUpdate.setPrice(price);
    }

    if (googleMapLocation != null && !googleMapLocation.isEmpty()) {
        houseToUpdate.setGoogleMapLocation(googleMapLocation);
    }

    if (file != null) {
        // Assuming an image file is provided, upload it and update the URL
        try {
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String newCoverImageUrl = (String) uploadResult.get("secure_url");
            houseToUpdate.setCoverImageUrl(newCoverImageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload new image");
        }
    }

    houseRepository.save(houseToUpdate);
    return houseToUpdate;
}

    // public Object getHousesForLoggedInUser(){

    // }

    // public House updateHouse(Long id, Map<String, Object> updates, MultipartFile file) {
    //     House houseToUpdate = houseRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("House not found with ID: " + id));
    
    //     updates.forEach((key, value) -> {
    //         switch (key) {
    //             case "title":
    //                 houseToUpdate.setTitle((String) value);
    //                 break;
    
    //             case "description":
    //                 houseToUpdate.setDescription((String) value);
    //                 break;
    
    //             case "price":
    //                 houseToUpdate.setPrice((String) value);
    //                 break;
    
    //             case "googleMapLocation":
    //                 houseToUpdate.setGoogleMapLocation((String) value);
    //                 break;
    
    //             case "coverImageUrl":
    //                 // Assuming an image file is provided, upload it and update the URL
    //                 try {
    //                     var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    //                     String newCoverImageUrl = (String) uploadResult.get("secure_url");
    //                     houseToUpdate.setCoverImageUrl(newCoverImageUrl);
    //                 } catch (IOException e) {
    //                     throw new RuntimeException("Failed to upload new image");
    //                 }
    //                 break;
    
    //             default:
    //                 // Handle unsupported fields or ignore them
    //                 // Add more cases for other fields to update
    //                 System.out.println("Unsupported field: " + key);
    //                 break;
    //         }
    //     });
    
    //     houseRepository.save(houseToUpdate);
    //     return houseToUpdate;
    // }

//     public House updateHouse(Long id, MultipartFile file, String title, String description, String price,
//     String googleMapLocation) {
// House houseToUpdate = houseRepository.findById(id)
// .orElseThrow(() -> new RuntimeException("House not found with ID: " + id));

// if (title != null) {
// houseToUpdate.setTitle(title);
// }

// if (description != null) {
// houseToUpdate.setDescription(description);
// }

// if (price != null) {
// houseToUpdate.setPrice(price);
// }

// if (googleMapLocation != null) {
// houseToUpdate.setGoogleMapLocation(googleMapLocation);
// }

// if (file != null) {
// try {
// var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
// String newCoverImageUrl = (String) uploadResult.get("secure_url");
// houseToUpdate.setCoverImageUrl(newCoverImageUrl);
// } catch (IOException e) {
// throw new RuntimeException("Failed to upload new image");
// }
// }

// houseRepository.save(houseToUpdate);
// return houseToUpdate;
// }

    

    

}
