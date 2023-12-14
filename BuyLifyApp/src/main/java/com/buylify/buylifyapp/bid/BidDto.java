package com.buylify.buylifyapp.bid;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.product.ProductDto;
import lombok.Data;

@Data
public class BidDto {

    private Long id;

    private float price;

    private ProductDto product;

    private String createdAt;
}
