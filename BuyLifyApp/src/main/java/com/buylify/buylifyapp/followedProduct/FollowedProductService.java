package com.buylify.buylifyapp.followedProduct;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.mappers.ProductMapper;
import com.buylify.buylifyapp.opinion.OpinionRepository;
import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.product.ProductDto;
import com.buylify.buylifyapp.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowedProductService {

    private final FollowedProductRepository followedProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OpinionRepository opinionRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllFollowedProducts(Long id) {
        return followedProductRepository.findByProductIdAndUserId(id).stream().map(followedProduct -> {
            ProductDto productDto = productMapper.toProductDto(followedProduct.getProduct());
            Float averageUserStars = opinionRepository.getUserAverageStars(followedProduct.getProduct().getUser().getId());
            productDto.getUser().setAverageStars(averageUserStars);
            return productDto;
        }).toList();
    }

    public void addFollowedProduct(CreateFollowedProductDto createFollowedProductDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        Product product = productRepository.findById(createFollowedProductDto.getId()).orElseThrow();

        FollowedProduct followedProduct = new FollowedProduct();
        followedProduct.setProduct(product);

        followedProduct.setUser(user);

        try {
            followedProductRepository.save(followedProduct);
        }
        catch (Exception e) {System.out.println(";)");}
    }
}
