package com.buylify.buylifyapp.models;

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
@Table(name = "delivery_methods")
public class DeliveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_method_sequence")
    @SequenceGenerator(name = "delivery_method_sequence", sequenceName = "delivery_method_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max=30)
    private String name;
}
