package com.buylify.buylifyapp.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditUserDto {

    @NotBlank
    @Size(min = 8, max = 30)
    private String username;

    @Size(max = 30)
    private String name;

    private String surname;

    private String phoneNumber;

    @NotBlank
    private String email;
}
