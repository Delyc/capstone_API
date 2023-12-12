// package com.househunting.api.controller;

// import com.cloudinary.Cloudinary;
// import com.cloudinary.utils.ObjectUtils;
// import com.househunting.api.entity.House;
// import com.househunting.api.repository.HouseRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.multipart.MultipartFile;

// import java.io.IOException;
// import java.util.Map;

// @RestController
// @RequestMapping("/posts")
// public class HouseController {

//     @Autowired
//     private HouseRepository blogPostRepository; // Assuming you have a repository for blog posts

//     @Autowired
//     private Cloudinary cloudinary; // Cloudinary bean configured in your application

//     @PostMapping("/create")
//     public House createPost(@RequestParam("file") MultipartFile file,
//                                @RequestParam("title") String title,
//                                @RequestParam("description") String description) throws IOException {

//         Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

//         String imageUrl = (String) uploadResult.get("url");

//         House post = House.builder()
//     .title(request.getTitle())
//     .description(request.getDescription())
//     .imageUrl(request.getImageUrl())
//     // Additional setters for other properties of BlogPost if available
//     .build();

// blogPostRepository.save(post);


//         return blogPostRepository.save(post);
//     }
// }

