package com.ilya.orderservice.util.validators;

import com.ilya.orderservice.dtos.UserDto;
import com.ilya.orderservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;
        if(userService.finOne(userDto.getEmail()) != null)
            errors.rejectValue("email", "", "Пользователь с таким email уже существует");
    }
}
