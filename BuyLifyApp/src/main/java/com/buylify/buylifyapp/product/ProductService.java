package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public void addProduct(CreateProductDto dto) {
        Product product = mapper.toEntity(dto);
        productRepository.save(product);
    }
}
