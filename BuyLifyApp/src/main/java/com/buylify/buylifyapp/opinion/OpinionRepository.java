package com.buylify.buylifyapp.opinion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    @Query("SELECT o FROM Opinion o " +
            "WHERE o.userSender.id = :userIdSender AND o.userReceiver.id = :userIdReceiver AND o.product.id = :productId")
    Optional<Opinion> findByUserSenderAndUserReceiver(@Param("userIdSender") Long userIdSender,
                                                      @Param("userIdReceiver") Long userIdReceiver,
                                                      @Param("productId") Long productId);

    @Query("SELECT AVG(o.stars) FROM Opinion o WHERE o.userReceiver.id = :userId")
    Float getUserAverageStars(@Param("userId") Long userId);

    @Query("SELECT AVG(o.stars) FROM Opinion o WHERE o.product.id = :productId")
    Float getProductAverageStars(@Param("productId") Long productId);

    @Query("SELECT o FROM Opinion o WHERE o.userReceiver.id = :userId")
    List<Opinion> findOpinionsByReceiverId(@Param("userId") Long userId);

    @Query("SELECT o FROM Opinion o WHERE o.product.id = :productId")
    List<Opinion> findOpinionsByProductId(@Param("productId") Long productId);
}
