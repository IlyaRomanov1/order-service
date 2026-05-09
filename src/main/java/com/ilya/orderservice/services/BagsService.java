package com.ilya.orderservice.services;

import com.ilya.orderservice.dtos.BagDto;
import com.ilya.orderservice.models.Bag;
import com.ilya.orderservice.models.User;
import com.ilya.orderservice.repositories.BagsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BagsService {

    private final BagsRepository bagsRepository;
    private final ModelMapper modelMapper;
    private final FoodsService foodsService;
    private final UserService userService;

    public List<BagDto> findAll(User user){
        return bagsRepository.findAllByUser(user)
                .stream().map(e -> mapToBagDto(user, e)).collect(Collectors.toList());
    }

    @Transactional
    public void save(BagDto bagDto){
        bagsRepository.save(mapToBag(bagDto));
    }

    @Transactional
    public void delete(Long id){
        bagsRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Long quantity){
        Optional<Bag> optBag = bagsRepository.findById(id);
        Bag bag = optBag.get();
        bag.setQuantity(quantity);
        bagsRepository.save(bag);
    }

    @Transactional
    public void delete(User user){
        bagsRepository.deleteAllByUser(user);
    }

    private BagDto mapToBagDto(User user, Bag bag){
        return new BagDto(bag.getId(), user.getId(), bag.getFood().getId(), bag.getFood().getName(),
                bag.getFood().getPrice(), bag.getQuantity());
    }

    private Bag mapToBag(BagDto bagDto){
        return new Bag(bagDto.getId(), userService.findOne(bagDto.getUserId()),
                foodsService.findFoodById(bagDto.getFoodId()), bagDto.getQuantity());
    }
}
