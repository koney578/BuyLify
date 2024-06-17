package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.address.AddressDto;
import com.buylify.buylifyapp.orderStatus.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductOrderDto {

    private ProductDto product;
    private Long orderId;
    private AddressDto address;
    private Long statusId;
}
