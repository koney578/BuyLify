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
}
