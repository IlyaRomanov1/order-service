package com.ilya.orderservice.controllers;

import com.ilya.orderservice.dtos.UserDto;
import com.ilya.orderservice.services.UserService;
import com.ilya.orderservice.util.exceptions.ErrorResponse;
import com.ilya.orderservice.util.exceptions.UserNotRegisterException;
import com.ilya.orderservice.util.validators.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid UserDto userDto, BindingResult bindingResult){
        userValidator.validate(userDto, bindingResult);
        if(bindingResult.hasErrors())
            throw new UserNotRegisterException(getErrorMessage(bindingResult));
        userService.register(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private String getErrorMessage(BindingResult bindingResult){
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for(FieldError error : errors)
            errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
        return errorMessage.toString();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserNotRegisterException e){
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
