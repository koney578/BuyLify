package com.buylify.buylifyapp.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderProduct {

    @NotNull
    private Long sellerId;

    @NotNull
    private Long productId;

    @NotNull
    private int productQuantity;
}
