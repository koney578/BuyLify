package com.buylify.buylifyapp.models;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_sequence")
    @SequenceGenerator(name = "discount_sequence", sequenceName = "discount_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private float discountPercent;

    @NotNull
    @CreationTimestamp
    private LocalDateTime startAt;

    @NotNull
    @CreationTimestamp
    private LocalDateTime endAt;
}