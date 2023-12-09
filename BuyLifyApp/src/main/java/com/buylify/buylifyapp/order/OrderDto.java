package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.paymentMethod.PaymentMethod;
import com.buylify.buylifyapp.product.Product;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    private Long id;

    private Address address;

    private PaymentMethod paymentMethod;

    private Product product;

    private int productQuantity;

    private String orderStatus;

    private float totalValue;

    private LocalDateTime createAt;

    private String deliveryMethod;
}
