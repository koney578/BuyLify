package com.buylify.buylifyapp.authentication;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDto {

    @NotBlank
    @Size(min = 8, max = 30)
    private String username;

    @NotBlank
    private String password;

//    @NotBlank
    @Size(max = 30)
    private String name;

//    @NotBlank
    private String surname;

//    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String email;

}
