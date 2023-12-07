package com.buylify.buylifyapp.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping
    public void addDiscount(Discount discount) {
        discountService.addDiscount(discount);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @DeleteMapping("/{id}")
    public void deleteDiscount(@PathVariable("id") Long id) {
        discountService.deleteDiscount(id);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PutMapping("/{id}")
    public void editDiscount(@PathVariable("id") Long id, Discount discountToEdit) {
        discountService.editDiscount(id, discountToEdit);
    }
}
