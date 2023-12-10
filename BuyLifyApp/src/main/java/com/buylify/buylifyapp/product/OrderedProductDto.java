package com.buylify.buylifyapp.product;

import lombok.Data;

@Data
public class OrderedProductDto {

    private Long id;

    private String name;

    private float price;

    private String description;

    private String photo;

    private int count;

    private int productQuantity;

    private float totalValue;
}
