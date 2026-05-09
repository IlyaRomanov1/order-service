package com.ilya.orderservice.controllers;

import com.ilya.orderservice.dtos.FoodDto;
import com.ilya.orderservice.dtos.FoodPatchDto;
import com.ilya.orderservice.services.FoodsService;

import com.ilya.orderservice.util.exceptions.ErrorResponse;

import com.ilya.orderservice.util.exceptions.FoodNotCreatedException;
import com.ilya.orderservice.util.exceptions.FoodNotFoundException;
import com.ilya.orderservice.util.exceptions.FoodNotUpdatedException;
import com.ilya.orderservice.util.validators.FoodCreateValidator;
import com.ilya.orderservice.util.validators.FoodUpdateValidator;
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
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodsController {

    private final FoodsService foodsService;
    private final FoodCreateValidator foodCreateValidator;
    private final FoodUpdateValidator foodUpdateValidator;

    @GetMapping("/{id}")
    public FoodDto findOne(@PathVariable("id") Long id){
        return foodsService.findOne(id);
    }

    @GetMapping
    public List<FoodDto> index(){
        return foodsService.findAll();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody @Valid FoodDto foodDto, BindingResult bindingResult){
        foodCreateValidator.validate(foodDto, bindingResult);

        if(bindingResult.hasErrors())
            throw new FoodNotCreatedException(getErrorMessage(bindingResult));

        foodsService.save(foodDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id){
        foodsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<HttpStatus> edit(@PathVariable("id") Long id, @RequestBody FoodPatchDto foodDto,
                                           BindingResult bindingResult){
        foodUpdateValidator.validate(foodDto, bindingResult);
        if(bindingResult.hasErrors())
            throw new FoodNotUpdatedException(getErrorMessage(bindingResult));

        foodsService.update(foodDto, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private String getErrorMessage(BindingResult bindingResult){
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for(FieldError error : fieldErrors){
            errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
        }
        return errorMessage.toString();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(FoodNotFoundException e){
        ErrorResponse foodNotFoundErrorResponse = new ErrorResponse(
                "Еды с таким id нет",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(foodNotFoundErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(FoodNotCreatedException e){
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(FoodNotUpdatedException e){
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

//Нужно создать новых челов чтоб у них пароль был зашфирован и накинуть им bags и протестить