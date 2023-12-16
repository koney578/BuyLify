package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.authentication.UserProductDto;
import com.buylify.buylifyapp.category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long Id;

    private String name;

    private float price;

    private String description;

    private String photo;

    private Category category;

    private LocalDateTime createdAt;

    private int count;

    private UserProductDto user;

    private LocalDateTime auctionEndsAt;


}
