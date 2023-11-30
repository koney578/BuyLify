package com.buylify.buylifyapp.deliveryMethod;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-methods")
@RequiredArgsConstructor
public class DeliveryMethodController {

    private final DeliveryMethodService deliveryMethodService;

    @GetMapping
    public List<DeliveryMethod> getAllDeliveryMethods() {
        return deliveryMethodService.getAllDeliveryMethods();
    }
}
