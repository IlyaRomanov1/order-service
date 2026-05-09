package com.ilya.orderservice.util.validators;

import com.ilya.orderservice.dtos.FoodDto;
import com.ilya.orderservice.dtos.FoodPatchDto;
import com.ilya.orderservice.services.FoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class FoodUpdateValidator implements Validator {
    private final FoodsService foodsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return FoodPatchDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FoodPatchDto food = (FoodPatchDto) target;
        if(food.getPrice() != null && food.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            errors.rejectValue("price", "", "Введите положительную цену");
        if(food.getName() != null){
            if(food.getName().isEmpty())
                errors.rejectValue("name", "", "Введите название еды");
            if(foodsService.findOne(food.getName()) != null)
                errors.rejectValue("name", "", "Такая еда уже есть");
        }
    }
}
