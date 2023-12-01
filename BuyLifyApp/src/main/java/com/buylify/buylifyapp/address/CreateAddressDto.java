package com.buylify.buylifyapp.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAddressDto {

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
