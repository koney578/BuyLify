package com.buylify.buylifyapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    private String name;
}
