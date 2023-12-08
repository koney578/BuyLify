package com.buylify.buylifyapp.bid;

import com.buylify.buylifyapp.authentication.User;
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
@Table(name = "bids")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bid_sequence")
    @SequenceGenerator(name = "bid_sequence", sequenceName = "bid_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(scale = 2)
    private float price;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @JoinColumn(name = "id_product", referencedColumnName = "id")
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    private Product product;

//    @NotNull
//    @Column(name = "id_product")
//    private Long idProduct;


    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

//    @NotNull
//    @Column(name = "id_user")
//    private Long idUser;
}
