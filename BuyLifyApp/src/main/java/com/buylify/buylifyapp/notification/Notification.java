package com.buylify.buylifyapp.notification;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.notificationType.NotificationType;
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
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_sequence")
    @SequenceGenerator(name = "notification_sequence", sequenceName = "notification_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    private String message;

    @NotNull
    private boolean isChecked;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

//    @NotNull
//    @Column(name = "id_user")
//    private Long idUser;

    @JoinColumn(name = "id_notification_type", referencedColumnName = "id")
    @ManyToOne(targetEntity = NotificationType.class, fetch = FetchType.EAGER)
    private NotificationType notificationType;

//    @NotNull
//    @Column(name = "id_notification_type")
//    private Long idType;
}