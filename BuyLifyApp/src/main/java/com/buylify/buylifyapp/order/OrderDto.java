package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.deliveryMethod.DeliveryMethod;
import com.buylify.buylifyapp.orderStatus.OrderStatus;
import com.buylify.buylifyapp.paymentMethod.PaymentMethod;
import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.product.ProductDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {

    private Long id;

    private Address address;

    private PaymentMethod paymentMethod;

    private ProductDto product;

    private int productQuantity;

    private OrderStatus orderStatus;

    private float totalValue;

    private LocalDateTime createAt;

    private DeliveryMethod deliveryMethod;
}
