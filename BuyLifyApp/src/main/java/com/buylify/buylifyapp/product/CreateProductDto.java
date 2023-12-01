package com.buylify.buylifyapp.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data


public class CreateProductDto {

    @NotNull
    private String name;

    @NotNull
    private float price;

    @NotNull
    @Size(max = 500)
    private String description;

    @NotNull
    private Long categoryId;

    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @NotNull
    private int count;

//    do zmiany
//    private byte[] photo;
}
