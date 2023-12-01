package com.buylify.buylifyapp.followedProduct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowedProductService {

    private final FollowedProductRepository followedProductRepository;

    public List<FollowedProduct> getAllFollowedProducts() {
        return followedProductRepository.findAll();
    }
}
