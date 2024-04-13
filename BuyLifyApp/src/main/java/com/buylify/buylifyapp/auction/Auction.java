package com.buylify.buylifyapp.auction;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.category.Category;
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
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auction_sequence")
    @SequenceGenerator(name = "auction_sequence", sequenceName = "auction_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private float startPrice;

    private float currentPrice;

    @NotNull
    private int count;

    @NotNull
    private String description;

    private String photo;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime endDate;

    private LocalDateTime lastBidDate;

    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    private Category category;

    @NotNull
    private boolean isActive;

    @JoinColumn(name = "id_owner", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @NotNull
    private User owner;

    @JoinColumn(name = "id_winner", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User winner;
}
