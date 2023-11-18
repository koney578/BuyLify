package com.buylify.buylifyapp.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data


public class CreateProductDto {

    @NotBlank
    private String name;

    @NotNull
    private float price;

    @NotBlank
    @Size(min = 20, max = 500)
    private String description;

    @NotNull
    private Long categoryId;

//    do zmiany
//    private byte[] photo;
}
