package com.buylify.buylifyapp.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductDto {
    @NotBlank
    private String name;
    @NotBlank
    private float price;
    @NotBlank
    @Size(min = 20, max = 500)
    private String description;
    @NotBlank
    private Category category;
//    do zmiany
//    private byte[] photo;

}
