package com.buylify.buylifyapp.orderDetails;

import com.buylify.buylifyapp.address.Address;
import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.deliveryMethod.DeliveryMethod;
import com.buylify.buylifyapp.order.Order;
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
@Table(name = "orders_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_details_sequence")
    @SequenceGenerator(name = "order_details_sequence", sequenceName = "order_details_sequence", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "id_order", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Order.class, fetch = FetchType.EAGER)
    private Order order;

    @NotNull
    @Column(name = "id_order")
    private Long idOrder;

    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    private Product product;

    @NotNull
    @Column(name = "id_product")
    private Long idProduct;

    @NotNull
    private int productQuantity;
}
