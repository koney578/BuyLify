package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.product.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {

    @NotNull
    List<OrderProduct> products;

    @NotNull
    List<OrderInfo> orderInfo;

    @NotNull
    private Address address;
}
