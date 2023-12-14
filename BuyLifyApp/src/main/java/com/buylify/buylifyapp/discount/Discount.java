package com.buylify.buylifyapp.discount;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    @Min(value = 0)
    @Max(value = 1)
    private float discountPercent;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;
}
