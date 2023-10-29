package com.buylify.buylifyapp.authentication;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    @Size(min = 8, max = 30)
    @Column(nullable = false, unique = true)
    private String userName;

    private String password;
    private String matchingPassword;

    @NotNull
    @Size(max = 30)
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Email
    private String eMail;
}
