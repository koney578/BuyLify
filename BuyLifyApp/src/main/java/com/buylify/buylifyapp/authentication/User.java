package com.buylify.buylifyapp.authentication;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 8, max = 30)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Size(max = 30)
//    @NotNull
    private String name;

//    @NotNull
    private String surname;

//    @NotNull
    private String phoneNumber;

    @NotBlank
    private String email;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    @NotNull
    private boolean isActive;
}
