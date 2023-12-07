package com.buylify.buylifyapp.paymentMethod;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        paymentMethodRepository.save(paymentMethod);
    }

    public void deletePaymentMethod(Long id) {
        paymentMethodRepository.deleteById(id);
    }

    public void editPaymentMethod(Long id, PaymentMethod paymentMethodToEdit) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow();
        paymentMethod.setName(paymentMethodToEdit.getName());
        paymentMethodRepository.save(paymentMethod);
    }
}
