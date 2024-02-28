package com.househunting.api.services;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.househunting.api.dto.HouseRequest;
import com.househunting.api.dto.HouseResponse;
import com.househunting.api.dto.WishlistResponse;
import com.househunting.api.entity.House;
import com.househunting.api.entity.Picture;
import com.househunting.api.entity.Video;
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
    
  
    public House createHouse(HouseRequest request) {
        User agent = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found for the given ID"));

        House house = new House();
        house.setTitle(request.getTitle());
        house.setDescription(request.getDescription());
        house.setCoverImageUrl(request.getCoverImageUrl()); 
        house.setPrice(request.getPrice());
      house.setLat(request.getLat());
      house.setLongi(request.getLongi());
      house.setStreetNumber(request.getStreetNumber());
        house.setAgent(agent);
house.setBedRooms(request.getBedRooms());
house.setTypeOfHouse(request.getTypeOfHouse());
        List<Picture> pictures = request.getPictureUrls().stream()
            .map(url -> new Picture(null, house, url))
            .collect(Collectors.toList());
        house.setPictures(pictures);

        List<Video> videos = request.getVideoUrls().stream()
            .map(url -> new Video(null, house, url))
            .collect(Collectors.toList());
        house.setVideos(videos);
        house.setFeatures(request.getFeatures());
        return houseRepository.save(house);
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
      houseResponse.setLongi(house.getLongi());
      houseResponse.setLat(house.getLat());
      houseResponse.setStreetNumber(house.getStreetNumber());
houseResponse.setBedRooms(house.getBedRooms());
houseResponse.setTypeOfHouse(house.getTypeOfHouse());
houseResponse.setFeatures(house.getFeatures());
        User agent = house.getAgent(); 
        if (agent != null) {
            houseResponse.setAgentId(agent.getId());
            houseResponse.setAgentEmail(agent.getEmail());
            houseResponse.setAgentPhoneNumber(agent.getPhone()); 
            houseResponse.setAgentName(agent.getFirstName() + " " + agent.getLastName()); 
        }

        // Process wishlists
        List<WishlistResponse> wishlistResponses = house.getWishlists().stream()
            .map(wishlist -> new WishlistResponse())
            .collect(Collectors.toList());
        houseResponse.setWishlists(wishlistResponses);

        // Process pictures
        List<String> pictureUrls = house.getPictures().stream()
            .map(Picture::getImageUrl) 
            .collect(Collectors.toList());
        houseResponse.setPictureUrls(pictureUrls);

        // Process videos
        List<String> videoUrls = house.getVideos().stream()
            .map(Video::getVideoUrl) 
            .collect(Collectors.toList());
        houseResponse.setVideoUrls(videoUrls);

        houseResponses.add(houseResponse);
    }

    return houseResponses;
}

    public HouseResponse getHouseById(Long houseId) {
        Optional<House> houseOptional = houseRepository.findById(houseId);
    
        if (houseOptional.isPresent()) {
            House house = houseOptional.get();

            System.out.printf("############################################", house);
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
            houseResponse.setBedRooms(house.getBedRooms());
            houseResponse.setTypeOfHouse(house.getTypeOfHouse());
            houseResponse.setFeatures(house.getFeatures());
            // Retrieve agent information
            User agent = house.getAgent(); 
            if (agent != null) {
                // Set agent-related fields in the response
                houseResponse.setAgentId(agent.getId());
                houseResponse.setAgentEmail(agent.getEmail());
                houseResponse.setAgentPhoneNumber(agent.getPhone());
                houseResponse.setAgentName(agent.getFirstName()); 
            }
    
            // Include wishlist information
            List<WishlistResponse> wishlistResponses = new ArrayList<>();
            for (Wishlist wishlist : house.getWishlists()) {
                WishlistResponse wishlistResponse = new WishlistResponse();
                wishlistResponse.setId(wishlist.getId());
                wishlistResponse.setId(wishlist.getUser().getId());
                // Set other wishlist-related fields
                wishlistResponses.add(wishlistResponse);
            }
            houseResponse.setWishlists(wishlistResponses);
          // Process pictures
          List<String> pictureUrls = house.getPictures().stream()
          .map(Picture::getImageUrl) 
          .collect(Collectors.toList());
      houseResponse.setPictureUrls(pictureUrls);

      // Process videos
      List<String> videoUrls = house.getVideos().stream()
          .map(Video::getVideoUrl) 
          .collect(Collectors.toList());
      houseResponse.setVideoUrls(videoUrls);
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
        // houseToUpdate.setGoogleMapLocation(googleMapLocation);
    }

    if (file != null) {
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

public List<HouseResponse> getHousesForUser(Long userId) {
    Optional<User> userOptional = userRepository.findById(userId);

    if (userOptional.isPresent()) {
        User user = userOptional.get();
        List<HouseResponse> houseResponses = new ArrayList<>();

        for (House house : user.getHouses()) {
            HouseResponse houseResponse = new HouseResponse();
            houseResponse.setId(house.getId());
            houseResponse.setTitle(house.getTitle());
            houseResponse.setPrice(house.getPrice());
            houseResponse.setCoverImageUrl(house.getCoverImageUrl());
            houseResponse.setDescription(house.getDescription());
            // houseResponse.setGoogleMapLocation(house.getGoogleMapLocation());

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
    } else {
        // Handle user not found
        return Collections.emptyList();
    }
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
