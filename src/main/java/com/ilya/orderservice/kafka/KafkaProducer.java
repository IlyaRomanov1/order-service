package com.ilya.orderservice.kafka;

import com.ilya.orderservice.dtos.OrderViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, OrderViewDto> kafkaTemplate;

    public void sendMessage(OrderViewDto orderViewDto){
        kafkaTemplate.send("order-send", orderViewDto);
    }
}
