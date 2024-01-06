package com.househunting.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.househunting.api.entity.Wishlist;
import com.househunting.api.repository.WishlistRepository;
import com.househunting.api.services.WishlistResponse;
import com.househunting.api.services.WishlistService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
// @RequestMapping("/api/v1/houses")
@RequiredArgsConstructor
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @Autowired
    WishlistRepository wishlistRepository;

    @PostMapping("/api/v1/wishlist/addHouse")
    public String addHouseToWishlist(
            @RequestParam("user_id") Long user_id,
            @RequestParam("house_id") Long house_id) {
        wishlistService.addHouseToWishlist(user_id, house_id);
        return "House added to the wishlist successfully!";
    }

    @DeleteMapping("/api/v1/wishlist/remove/{wishlistId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long wishlistId) {
        try {
            Optional<Wishlist> wishlistEntry = wishlistRepository.findById(wishlistId);
            if (wishlistEntry.isPresent()) {
                Wishlist entryToRemove = wishlistEntry.get();

                // Remove references to the entry from associated entities (user and house)
                entryToRemove.getUser().getWishlists().remove(entryToRemove);
                entryToRemove.getHouse().getWishlists().remove(entryToRemove);

                // Delete the entry from the wishlist
                wishlistRepository.delete(entryToRemove);

                return ResponseEntity.ok("Wishlist item removed successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wishlist item not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while removing the wishlist item");
        }
    }

    @DeleteMapping("/api/v1/wishlist/empty/{user_id}")
    public String emptyWishlist(@PathVariable Long user_id) {
        wishlistService.emptyWishlistByUserId(user_id);
        return "Wishlist emptied successfully!";
    }

    
    @GetMapping("/api/v1/wishlist/get/{user_id}")
public ResponseEntity<List<WishlistResponse>> getUserWishlistWithHouseDetails(@PathVariable Long user_id) {
    List<WishlistResponse> userWishlist = wishlistService.getUserWishlistWithHouseDetails(user_id);
    return ResponseEntity.ok(userWishlist);
}

@PostMapping("/api/v1/wishlist/share/{user_id}")
public ResponseEntity<String> shareWishlist(@PathVariable Long user_id, @RequestBody String recipientEmail){
    wishlistService.shareWishlist(user_id, "delyceu@gmail.com");
    return ResponseEntity.ok("Wishlist shared");
}


}
