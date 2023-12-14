package com.buylify.buylifyapp.bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("SELECT MAX(b.price) FROM Bid b WHERE b.product.id = :productId")
    Float getMaxBidPrice(@Param("productId") Long productId);
}
