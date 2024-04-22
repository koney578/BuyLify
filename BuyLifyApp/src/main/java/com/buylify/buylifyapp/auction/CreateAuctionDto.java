package com.buylify.buylifyapp.auction;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAuctionDto {

    @NotNull
    private String name;

    @NotNull
    private float startPrice;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private Long categoryId;
}
