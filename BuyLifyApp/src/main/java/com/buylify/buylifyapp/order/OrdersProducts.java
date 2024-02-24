package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrdersProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_products_sequence")
    @SequenceGenerator(name = "orders_products_sequence", sequenceName = "orders_products_sequence", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "id_order", referencedColumnName = "id")
    @ManyToOne(targetEntity = Order.class, fetch = FetchType.EAGER)
    private Order order;

    @JoinColumn(name = "id_product", referencedColumnName = "id")
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    private Product product;

    @NotNull
    private int productQuantity;

    @NotNull
    @Column(scale = 2)
    private float sumPrice;
}
