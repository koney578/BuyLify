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
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_status_sequence")
    @SequenceGenerator(name = "order_status_sequence", sequenceName = "order_status_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 30)
    private String name;
}
