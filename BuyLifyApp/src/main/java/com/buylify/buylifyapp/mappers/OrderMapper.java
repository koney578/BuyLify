package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.order.CreateOrderDto;
import com.buylify.buylifyapp.order.Order;
import com.buylify.buylifyapp.order.OrderDto;
import com.buylify.buylifyapp.product.CreateProductDto;
import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.product.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {
    @Mapping(target = "address", ignore = true)
    Order toEntity(CreateOrderDto dto);

    OrderDto toDto(Order order);

//    ProductDto toProductDto(Product product);
}
