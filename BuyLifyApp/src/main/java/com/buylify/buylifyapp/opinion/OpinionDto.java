package com.buylify.buylifyapp.opinion;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OpinionDto {

    private int stars;

    private String description;

    private LocalDateTime createdAt;

    private String username;
}
