package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.product.Product;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {

//    @NotNull
//    private Address address;
//
//    @NotNull
//    private Long idPaymentMethod;
//
//    @NotNull
//    private Long idDeliveryMethod;
//
//    @NotNull
//    private Long idProduct;
//
//    @NotNull
//    private int productQuantity;

    List<OrderProduct> products;

    List<OrderInfo> orderInfo;
}
