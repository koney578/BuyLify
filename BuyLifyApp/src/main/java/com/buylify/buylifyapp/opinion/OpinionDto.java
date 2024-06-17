package com.buylify.buylifyapp.opinion;

import com.buylify.buylifyapp.authentication.UserDto;
import com.buylify.buylifyapp.authentication.UserProductDto;
import com.buylify.buylifyapp.product.ProductDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpinionDto {

    private Long id;

    private int stars;

    private String description;

    private LocalDateTime createdAt;

    private Long userId;

    private String username;

    private ProductDto product;


}
