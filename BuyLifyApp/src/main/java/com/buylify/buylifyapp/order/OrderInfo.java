package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.address.Address;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderInfo {

    @NotNull
    private Long sellerId;

    @NotNull
    private Long idPaymentMethod;

    @NotNull
    private Long idDeliveryMethod;
}
