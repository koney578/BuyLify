package com.buylify.buylifyapp.authentication;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @Size(min = 8, max = 30)
    @Column(nullable = false, unique = true)
    private String userName;

    private String password;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Email
    private String eMail;

    @CreationTimestamp
    @NotNull
    private LocalDateTime registrationDate;

    @NotNull
    private boolean isActive;
}
