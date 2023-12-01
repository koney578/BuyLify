package com.buylify.buylifyapp.address;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {

    private Long id;

    private String name;

    private String surname;

    private String phoneNumber;

    private String email;

    private String country;

    private String city;

    private String houseUnitNumber;

    private String postalCode;
}
