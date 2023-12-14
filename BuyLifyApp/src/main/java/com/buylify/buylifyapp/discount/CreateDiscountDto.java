package com.buylify.buylifyapp.discount;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDiscountDto {

    private float discountPercent;
    private int days;
}
