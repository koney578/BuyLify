package com.buylify.buylifyapp.paymentMethod;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_method_sequence")
    @SequenceGenerator(name = "payment_method_sequence", sequenceName = "payment_method_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;
}
