package com.buylify.buylifyapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    @SequenceGenerator(name = "address_sequence", sequenceName = "address_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max=30)
    private String name;

    @NotNull
    @Size(max=30)
    private String surname;

    @NotNull
    @Size(min = 9, max=9)
    private String phoneNumber;

    @NotNull
    @Size(max=50)
    private String email;

    @NotNull
    @Size(max=56)
    private String country;

    @NotNull
    @Size(max=50)
    private String city;

    @Size(max=60)
    private String street;

    @NotNull
    @Size(max=8)
    private String houseUnitNumber;

    @NotNull
    @Size(max=6)
    private String postalCode;
}
