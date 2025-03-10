package com.buylify.buylifyapp.followedProduct;

import com.buylify.buylifyapp.deliveryMethod.DeliveryMethod;
import com.buylify.buylifyapp.product.ProductDto;
import com.buylify.buylifyapp.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/followed-products")
@RequiredArgsConstructor
public class FollowedProductController {

    private final FollowedProductService followedProductService;

    @PostMapping
    public void addFollowedProduct(@RequestBody CreateFollowedProductDto createFollowedProductDto, Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        followedProductService.addFollowedProduct(createFollowedProductDto, userId);
    }

    @GetMapping
    public List<ProductDto> getFollowedProducts(Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        return followedProductService.getAllFollowedProducts(userId);
    }

    @DeleteMapping("/{productId}")
    public void removeFollowedProduct(@PathVariable("productId") Long productId, Authentication authentication){
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        followedProductService.removeFollowedProduct(userId,productId);
    }
}
