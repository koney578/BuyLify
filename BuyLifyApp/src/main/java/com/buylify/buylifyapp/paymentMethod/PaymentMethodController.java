package com.buylify.buylifyapp.paymentMethod;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @GetMapping
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodService.getAllPaymentMethods();
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping
    public void addPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        paymentMethodService.addPaymentMethod(paymentMethod);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @DeleteMapping("/{id}")
    public void deletePaymentMethod(@PathVariable("id") Long id) {
        paymentMethodService.deletePaymentMethod(id);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PutMapping("/{id}")
    public void editPaymentMethod(@PathVariable("id") Long id, @RequestBody PaymentMethod paymentMethodToEdit) {
        paymentMethodService.editPaymentMethod(id, paymentMethodToEdit);
    }
}
