package com.buylify.buylifyapp.authentication;
import com.buylify.buylifyapp.userType.UserType;
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
    private Long id;

    @Column(unique = true)
    @Size(min = 5, max = 40)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @Size(max = 30)
    private String name;

    @Size(max = 30)
    private String surname;

    @Size(min = 9, max = 9)
    private String phoneNumber;

    @NotNull
    @Size(max = 60)
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registrationDate;

    @NotNull
    private boolean isActive;

    @JoinColumn(name = "id_user_type", insertable = false, updatable = false)
    @ManyToOne(targetEntity = UserType.class, fetch = FetchType.EAGER)
    private UserType userType;

    @NotNull
    @Column(name = "id_user_type")
    private Long idUserType;
}
