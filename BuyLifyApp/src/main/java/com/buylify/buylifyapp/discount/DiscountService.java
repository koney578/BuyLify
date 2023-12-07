package com.buylify.buylifyapp.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public void addDiscount(Discount discount) {
        discountRepository.save(discount);
    }

    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }


    public void editDiscount(Long id, Discount discountToEdit) {
        Discount discount = discountRepository.findById(id).orElseThrow();
        discount.setDiscountPercent(discountToEdit.getDiscountPercent());
        discount.setStartAt(discountToEdit.getStartAt());
        discount.setEndAt(discountToEdit.getEndAt());
        discountRepository.save(discount);
    }
}
