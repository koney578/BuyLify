package com.buylify.buylifyapp.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateProductDto {

    @NotBlank
    private String name;

    @NotBlank
    private float price;

    @NotBlank
    @Size(min = 20, max = 500)
    private String description;

    @NotBlank
    private Long categoryId;

//    do zmiany
//    private byte[] photo;
}
