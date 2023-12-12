package com.househunting.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public CloudinaryUploadResponse uploadProfilePicture(MultipartFile multipartFile) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = uploadResult.get("url").toString();
            return new CloudinaryUploadResponse(imageUrl, null); // Return successful response
        } catch (IOException e) {
            e.printStackTrace();
            return new CloudinaryUploadResponse(null, "Failed to upload image to Cloudinary"); // Return error response
        }
    }

    public static class CloudinaryUploadResponse {
        private final String imageUrl;
        private final String error;

        public CloudinaryUploadResponse(String imageUrl, String error) {
            this.imageUrl = imageUrl;
            this.error = error;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getError() {
            return error;
        }
    }
}
