package com.buylify.buylifyapp.discount;

import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    @PatchMapping("/{productId}")
    public void addDiscount(@RequestBody CreateDiscountDto discountDto, @PathVariable("productId") Long productId, Authentication authentication){
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();


        discountService.addDiscount(discountDto, productId, userId);
    }

}
