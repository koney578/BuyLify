package com.buylify.buylifyapp.followedProduct;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/followed-products")
@RequiredArgsConstructor
public class FollowedProductController {

    private final FollowedProductService followedProductService;

    @GetMapping
    public List<FollowedProduct> getAllDiscounts() {
        return followedProductService.getAllFollowedProducts();
    }
}
