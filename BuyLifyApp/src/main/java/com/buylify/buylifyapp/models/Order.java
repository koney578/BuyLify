package com.buylify.buylifyapp.models;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "id_address", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Address.class, fetch = FetchType.EAGER)
    private Address address;

    @NotNull
    @Column(name = "id_address")
    private Long idAddress;

    @JoinColumn(name = "id_payment_method", insertable = false, updatable = false)
    @ManyToOne(targetEntity = PaymentMethod.class, fetch = FetchType.EAGER)
    private PaymentMethod paymentMethod;

    @NotNull
    @Column(name = "id_payment_method")
    private Long idPaymentMethod;

    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    private Product product;

    @NotNull
    @Column(name = "id_product")
    private Long idProduct;

    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @NotNull
    @Column(name = "id_user")
    private Long idUser;

    @NotNull
    private int productQuantity;

    @NotNull
    @Column(scale = 2)
    private float totalValue;

    @NotNull
    @CreationTimestamp
    private LocalDateTime createAt;

    @JoinColumn(name = "id_delivery_method", insertable = false, updatable = false)
    @ManyToOne(targetEntity = DeliveryMethod.class, fetch = FetchType.EAGER)
    private DeliveryMethod deliveryMethod;

    @NotNull
    @Column(name = "id_delivery_method")
    private Long idDeliveryMethod;


    @JoinColumn(name = "id_order_status", insertable = false, updatable = false)
    @ManyToOne(targetEntity = OrderStatus.class, fetch = FetchType.EAGER)
    private OrderStatus orderStatus;

    @NotNull
    @Column(name = "id_order_status")
    private Long idOrderStatus;
}
