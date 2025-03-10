package com.buylify.buylifyapp.opinion;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "opinions")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opinion_sequence")
    @SequenceGenerator(name = "opinion_sequence", sequenceName = "opinion_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private int stars;

    @Size(max = 400)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @JoinColumn(name = "id_user_sender", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User userSender;

    @JoinColumn(name = "id_user_receiver", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User userReceiver;

    @JoinColumn(name = "id_product", referencedColumnName = "id")
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    private Product product;
}
