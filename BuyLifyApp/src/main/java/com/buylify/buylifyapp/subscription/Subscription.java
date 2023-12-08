package com.buylify.buylifyapp.subscription;

import com.buylify.buylifyapp.authentication.User;
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
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscription_sequence")
    @SequenceGenerator(name = "subscription_sequence", sequenceName = "subscription_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime startAt;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime endAt;

    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

//    @NotNull
//    @Column(name = "id_user")
//    private Long idUser;
}
