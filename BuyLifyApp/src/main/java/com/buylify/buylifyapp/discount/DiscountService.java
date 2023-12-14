package com.buylify.buylifyapp.discount;

import com.buylify.buylifyapp.product.Product;
import com.buylify.buylifyapp.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public void addDiscount(CreateDiscountDto discountDto,Long productId, Long userId) {
        Product product = productRepository.findById(productId).orElseThrow();

        if (!product.getUser().getId().equals(userId)){
            return;
        }

        if (product.getDiscount() != null){
            Discount oldDiscount = discountRepository.findById(product.getDiscount().getId()).orElseThrow();
            oldDiscount.setEndAt(LocalDateTime.now());
            discountRepository.save(oldDiscount);
        }

        Discount discount = new Discount();
        discount.setDiscountPercent(discountDto.getDiscountPercent());
        discount.setEndAt(LocalDateTime.now().plusDays(discountDto.getDays()));
        discountRepository.save(discount);

        product.setDiscount(discount);
        productRepository.save(product);
    }

}
