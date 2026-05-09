package com.ilya.orderservice.services;

import com.ilya.orderservice.dtos.OrderViewDto;
import com.ilya.orderservice.kafka.KafkaProducer;
import com.ilya.orderservice.models.User;
import com.ilya.orderservice.repositories.OrderViewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderViewService {

    private final OrderViewRepository orderViewRepository;
    private final ModelMapper modelMapper;
    private final BagsService bagsService;
    private final KafkaProducer kafkaProducer;

    @Transactional
    public void send(User user){
        bagsService.delete(user);
        kafkaProducer.sendMessage( modelMapper.map(orderViewRepository.findByUserId(user.getId()), OrderViewDto.class));
    }
}
