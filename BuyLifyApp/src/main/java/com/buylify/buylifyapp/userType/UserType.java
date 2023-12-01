package com.buylify.buylifyapp.userType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_types")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_types_sequence")
    @SequenceGenerator(name = "user_types_sequence", sequenceName = "user_types_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(unique = true)
    private String name;
}