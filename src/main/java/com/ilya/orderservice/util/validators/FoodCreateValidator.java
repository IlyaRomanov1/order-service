package com.ilya.orderservice.util.validators;

import com.ilya.orderservice.dtos.FoodDto;
import com.ilya.orderservice.services.FoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FoodCreateValidator implements Validator {
    private final FoodsService foodsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return FoodDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FoodDto foodDto = (FoodDto) target;
        if(foodsService.findOne(foodDto.getName()) != null)
            errors.rejectValue("name", "", "Еда с таким именем уже существует");
    }
}
