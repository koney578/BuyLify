package com.buylify.buylifyapp.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductOrderDto {

    private ProductDto product;
    private Long orderId;
}
