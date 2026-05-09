package com.ilya.orderservice.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FoodPatchDto {
    private String name;

    private BigDecimal price;
}
