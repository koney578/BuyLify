package com.buylify.buylifyapp.authentication;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class UserDto {

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
