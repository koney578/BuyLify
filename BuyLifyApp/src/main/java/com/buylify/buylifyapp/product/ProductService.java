package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public void addProduct(CreateProductDto dto, Long userId) {
        Product product = mapper.toEntity(dto);
        product.setActive(true);
        product.setIdUser(userId);
        productRepository.save(product);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toProductDto)
                .toList();
    }

    public ProductDto getProductById(Long id) {
        return mapper.toProductDto(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found")));
    }
}
