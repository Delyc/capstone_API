package com.househunting.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.househunting.api.entity.House;
import com.househunting.api.entity.Wishlist;
import com.househunting.api.user.User;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    // @Modifying
    // @Transactional
    // @Query("DELETE FROM Wishlist w WHERE w.user.id = :user_id")
    // void deleteByUserId(@Param("user_id") Long user_id);
    void deleteByUserId(Long userId);

    Wishlist findByUserAndHouse(User user, House house);
}
