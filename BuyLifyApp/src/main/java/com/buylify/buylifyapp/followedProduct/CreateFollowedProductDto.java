package com.buylify.buylifyapp.followedProduct;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFollowedProductDto {

    @NotNull
    private Long id;

}
