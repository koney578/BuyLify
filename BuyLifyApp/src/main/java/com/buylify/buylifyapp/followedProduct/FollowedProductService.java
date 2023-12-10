package com.buylify.buylifyapp.followedProduct;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.product.Product;
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

    public List<FollowedProduct> getAllFollowedProducts(Long id) {
        return followedProductRepository.findByProductIdAndUserId(id);
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
