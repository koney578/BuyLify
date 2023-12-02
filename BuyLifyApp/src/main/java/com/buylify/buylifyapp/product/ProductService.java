package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final String IMAGE_PREFIX = "https://firebasestorage.googleapis.com/v0/b/projekt-zespolowy-5362c.appspot.com/o/";
    public static final String MEDIA_PARAM = "?alt=media&token=";


    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public void addProduct(CreateProductDto post, String fileName, Long userId) {
        Product product = mapper.toEntity(post);
        String imageUrl = IMAGE_PREFIX + fileName + MEDIA_PARAM + fileName;
        product.setPhoto(imageUrl);
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
