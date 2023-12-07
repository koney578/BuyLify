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

    public void addDeliveryMethod(DeliveryMethod deliveryMethod) {
        deliveryMethodRepository.save(deliveryMethod);
    }

    public void deleteDeliveryMethod(Long id) {
        deliveryMethodRepository.deleteById(id);
    }


    public void editDeliveryMethod(Long id, DeliveryMethod deliveryMethodToEdit) {
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(id).orElseThrow();
        deliveryMethod.setName(deliveryMethodToEdit.getName());
        deliveryMethodRepository.save(deliveryMethod);
    }
}
