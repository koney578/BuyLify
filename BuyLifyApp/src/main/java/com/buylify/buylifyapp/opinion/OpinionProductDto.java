package com.buylify.buylifyapp.opinion;

import lombok.Data;

@Data
public class OpinionProductDto {

    private Long id;

    private String name;

    private float price;

    private String description;

    private String photo;

    private int count;

    private int productQuantity;

    private float totalValue;

    private OpinionDto opinion;
}
