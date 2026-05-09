package com.ilya.orderservice.controllers;

import com.ilya.orderservice.dtos.OrderViewDto;
import com.ilya.orderservice.security.MyUserDetails;
import com.ilya.orderservice.services.OrderViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderViewService orderViewService;

    @PostMapping("/send")
    public ResponseEntity<HttpStatus> send(@AuthenticationPrincipal MyUserDetails userDetails){
        orderViewService.send(userDetails.getUser());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
