package com.buylify.buylifyapp.authentication;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class UserDto {

    private String username;

    private String name;

    private String surname;

    private String phoneNumber;

    private String email;

    private LocalDateTime registrationDate;

    Collection<? extends GrantedAuthority> roles;
}
