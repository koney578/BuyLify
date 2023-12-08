package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.category.Category;
import com.buylify.buylifyapp.discount.Discount;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private float price;

    @NotNull
    private int count;

    @NotNull
    @Size(max=500)
    private String description;

    @NotNull
    private int priority;

    private String photo;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    private Category category;

//    @Column(name = "category_id")
//    private Long categoryId;

    @JoinColumn(name = "id_discount", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Discount.class, fetch = FetchType.EAGER)
    private Discount discount;

    @Column(name = "id_discount")
    private Long idDiscount;

    @NotNull
    private boolean isActive;

    private LocalDateTime auctionEndsAt;

    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @NotNull
    @Column(name = "id_user")
    private Long idUser;
}
