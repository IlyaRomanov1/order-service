package com.ilya.orderservice.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FoodDto {
    private Long id;

    @NotBlank(message = "Введите название")
    private String name;

    @Positive(message = "Введите положительную сумму")
    private BigDecimal price;
}
