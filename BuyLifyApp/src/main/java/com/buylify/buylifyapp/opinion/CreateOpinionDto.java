package com.buylify.buylifyapp.opinion;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOpinionDto {

    @NotNull
    private Long receiverId;

    @Min(value = 0)
    @Max(value = 10)
    private int stars;

    @Size(max = 400)
    private String description;

    @NotNull
    private Long productId;

}
