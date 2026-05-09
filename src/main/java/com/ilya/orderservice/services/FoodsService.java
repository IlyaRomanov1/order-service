package com.ilya.orderservice.services;

import com.ilya.orderservice.dtos.FoodPatchDto;
import com.ilya.orderservice.util.exceptions.FoodNotFoundException;
import com.ilya.orderservice.dtos.FoodDto;
import com.ilya.orderservice.models.Food;
import com.ilya.orderservice.repositories.FoodsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodsService {

    private final FoodsRepository foodsRepository;
    private final ModelMapper modelMapper;



    public FoodDto findOne(Long id){
        Optional<Food> food = foodsRepository.findById(id);
        if(food.isEmpty()){
            throw new FoodNotFoundException();
        }
        return modelMapper.map(food, FoodDto.class);
    }

    public Food findFoodById(Long id){
        Optional<Food> food = foodsRepository.findById(id);
        if(food.isEmpty()){
            throw new FoodNotFoundException();
        }
        return food.get();
    }


    public List<FoodDto> findAll(){
        List<FoodDto> foodDtos = foodsRepository.findAll().stream()
                .map(e -> modelMapper.map(e, FoodDto.class)).collect(Collectors.toList());
        return foodDtos;
    }


    public String findOne(String name){
        Optional<Food> food = foodsRepository.findByName(name);
        return food.map(Food::getName).orElse(null);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void save(FoodDto foodDto){
        foodsRepository.save(modelMapper.map(foodDto, Food.class));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id){
        foodsRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void update(FoodPatchDto foodDto, Long id){
        Optional<Food> foodOpt = foodsRepository.findById(id);
        Food food = foodOpt.get();
        if(foodDto.getName() != null) food.setName(foodDto.getName());
        if(foodDto.getPrice() != null) food.setPrice(foodDto.getPrice());
        foodsRepository.save(food);
    }

}
