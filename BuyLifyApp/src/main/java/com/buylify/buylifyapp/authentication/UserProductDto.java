package com.buylify.buylifyapp.authentication;

import lombok.Data;

@Data
public class UserProductDto {

    private Long id;

    private String username;

    private String email;

    private Float averageStars;
}
