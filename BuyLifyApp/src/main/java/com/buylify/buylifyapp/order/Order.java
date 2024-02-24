package com.buylify.buylifyapp.order;

import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.deliveryMethod.DeliveryMethod;
import com.buylify.buylifyapp.orderStatus.OrderStatus;
import com.buylify.buylifyapp.paymentMethod.PaymentMethod;
import com.buylify.buylifyapp.product.Product;
import jakarta.persistence.*;
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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "id_address", referencedColumnName = "id")
    @ManyToOne(targetEntity = Address.class, fetch = FetchType.EAGER)
    private Address address;

    @JoinColumn(name = "id_payment_method", referencedColumnName = "id")
    @ManyToOne(targetEntity = PaymentMethod.class, fetch = FetchType.EAGER)
    private PaymentMethod paymentMethod;

    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @NotNull
    @Column(scale = 2)
    private float totalValue;

    @NotNull
    private int productQuantity;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @JoinColumn(name = "id_delivery_method", referencedColumnName = "id")
    @ManyToOne(targetEntity = DeliveryMethod.class, fetch = FetchType.EAGER)
    private DeliveryMethod deliveryMethod;

    @JoinColumn(name = "id_order_status", referencedColumnName = "id")
    @ManyToOne(targetEntity = OrderStatus.class, fetch = FetchType.EAGER)
    private OrderStatus orderStatus;
}
