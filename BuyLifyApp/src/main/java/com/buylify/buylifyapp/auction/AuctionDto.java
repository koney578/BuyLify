package com.buylify.buylifyapp.auction;

import com.buylify.buylifyapp.authentication.UserProductDto;
import com.buylify.buylifyapp.category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AuctionDto {
    private Long id;
    private String name;
    private float startPrice;
    private float currentPrice;
    private String description;
    private String photo;
    private Category category;
    private UserProductDto owner;
    private UserProductDto winner;
    private LocalDateTime createdAt;
    private LocalDateTime endDate;
    private LocalDateTime lastBidDate;
}
