package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.product.CreateProductDto;
import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.product.ProductDto;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    Product toEntity(CreateProductDto dto);

    ProductDto toProductDto(Product product);
}
