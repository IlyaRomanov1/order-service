package com.ilya.orderservice.dtos;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BagPatchDto {
    private Long id;
    @Positive(message = "Введите положительную сумму")
    private Long quantity;
}
