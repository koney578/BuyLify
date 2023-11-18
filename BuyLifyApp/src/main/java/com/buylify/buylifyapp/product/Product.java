package com.buylify.buylifyapp.product;

import com.buylify.buylifyapp.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    private String name;

    @NotNull
    private float price;

    @NotNull
    private int count;

    @NotBlank
    private String description;

    @NotNull
    private int priority;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    private Category category;

    @Column(name = "category_id")
    private Long categoryId;

}
