package com.buylify.buylifyapp.followedProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowedProductRepository extends JpaRepository<FollowedProduct, Long> {
    @Query("SELECT fp FROM FollowedProduct fp WHERE fp.user.id = :userId")
    List<FollowedProduct> findByProductIdAndUserId(@Param("userId") Long userId);
}
