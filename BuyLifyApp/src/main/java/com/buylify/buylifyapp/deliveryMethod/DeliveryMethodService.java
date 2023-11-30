package com.buylify.buylifyapp.deliveryMethod;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryMethodService {

    private final DeliveryMethodRepository deliveryMethodRepository;

    public List<DeliveryMethod> getAllDeliveryMethods() {
        return deliveryMethodRepository.findAll();
    }
}
