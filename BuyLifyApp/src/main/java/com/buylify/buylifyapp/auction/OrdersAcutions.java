package com.buylify.buylifyapp.auction;

import com.buylify.buylifyapp.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders_auctions")
public class OrdersAcutions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_auctions_sequence")
    @SequenceGenerator(name = "orders_auctions_sequence", sequenceName = "orders_auctions_sequence", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "id_order", referencedColumnName = "id")
    @ManyToOne(targetEntity = Order.class, fetch = FetchType.EAGER)
    private Order order;

    @JoinColumn(name = "id_auction", referencedColumnName = "id")
    @ManyToOne(targetEntity = Auction.class, fetch = FetchType.EAGER)
    private Auction auction;

}
