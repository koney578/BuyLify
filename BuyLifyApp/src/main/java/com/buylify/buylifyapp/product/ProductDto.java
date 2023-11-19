package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.category.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {

    private Long Id;

    private String name;

    private float price;

    private String description;

    private Category category;

    private LocalDateTime createdDate;

//    do zmiany
//    private byte[] photo;

}
