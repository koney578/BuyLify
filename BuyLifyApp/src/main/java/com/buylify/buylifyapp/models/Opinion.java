package com.buylify.buylifyapp.models;


import com.buylify.buylifyapp.authentication.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Max(value = 10)
    private int stars;

    @Size(max = 400)
    private String description;

    @JoinColumn(name = "id_user_sender", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User userSender;

    @NotNull
    @Column(name = "id_user_sender")
    private Long idUserSender;

    @JoinColumn(name = "id_user_receiver", insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User userReceiver;

    @NotNull
    @Column(name = "id_user_receiver")
    private Long idUserReceiver;
}
