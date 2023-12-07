package com.buylify.buylifyapp.deliveryMethod;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping
    public void addDeliveryMethod(DeliveryMethod deliveryMethod) {
        deliveryMethodService.addDeliveryMethod(deliveryMethod);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @DeleteMapping("/{id}")
    public void deleteDeliveryMethod(@PathVariable("id") Long id) {
        deliveryMethodService.deleteDeliveryMethod(id);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PutMapping("/{id}")
    public void editDeliveryMethod(@PathVariable("id") Long id, DeliveryMethod deliveryMethodToEdit) {
        deliveryMethodService.editDeliveryMethod(id, deliveryMethodToEdit);
    }
}
