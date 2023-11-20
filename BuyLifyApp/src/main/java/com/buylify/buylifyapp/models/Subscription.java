package com.buylify.buylifyapp.models;

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

    @NotNull
    @CreationTimestamp
    private LocalDateTime startAt;

    @NotNull
    @CreationTimestamp
    private LocalDateTime endAt;

    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @NotNull
    @Column(name = "id_user")
    private Long idUser;

}
