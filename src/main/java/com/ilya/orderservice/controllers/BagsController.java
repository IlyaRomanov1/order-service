package com.ilya.orderservice.controllers;

import com.ilya.orderservice.dtos.BagDto;
import com.ilya.orderservice.dtos.BagPatchDto;
import com.ilya.orderservice.models.Food;
import com.ilya.orderservice.security.MyUserDetails;
import com.ilya.orderservice.services.BagsService;
import com.ilya.orderservice.services.FoodsService;
import com.ilya.orderservice.services.MyUserDetailsService;
import com.ilya.orderservice.util.exceptions.BagNotCreatedException;
import com.ilya.orderservice.util.exceptions.BagNotUpdateExcpetion;
import com.ilya.orderservice.util.exceptions.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bags")
public class BagsController {

    private final BagsService bagsService;
    private final FoodsService foodsService;

    @GetMapping
    public List<BagDto> getUsersBags(@AuthenticationPrincipal MyUserDetails myUserDetails){
        return bagsService.findAll(myUserDetails.getUser());
    }

    @PostMapping
    //Пользователь отправляет лишь id еды и кол-во
    public ResponseEntity<HttpStatus> addFood(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                              @RequestBody @Valid BagDto bagDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BagNotCreatedException(getErrorMessage(bindingResult));
        }

        bagDto.setUserId(myUserDetails.getUser().getId());
        Food food = foodsService.findFoodById(bagDto.getFoodId());
        bagDto.setFoodName(food.getName());
        bagDto.setFoodPrice(food.getPrice());

        bagsService.save(bagDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFoodFromBag(@PathVariable Long id){
        bagsService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/edit")
    //только quantity
    public ResponseEntity<HttpStatus> updateBag(@PathVariable Long id,@RequestBody @Valid BagPatchDto bagPatchDto,
                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BagNotUpdateExcpetion(getErrorMessage(bindingResult));
        }

        bagsService.update(bagPatchDto.getId(), bagPatchDto.getQuantity());
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
    private ResponseEntity<ErrorResponse> handleException(BagNotCreatedException e){
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(BagNotUpdateExcpetion e){
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
