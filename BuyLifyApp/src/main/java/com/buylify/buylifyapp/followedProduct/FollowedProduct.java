package com.buylify.buylifyapp.followedProduct;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "followed_products", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_product", "id_user"})})
public class FollowedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "followed_product_sequence")
    @SequenceGenerator(name = "followed_product_sequence", sequenceName = "followed_product_sequence", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "id_product", referencedColumnName = "id")
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    private Product product;

    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;
}
