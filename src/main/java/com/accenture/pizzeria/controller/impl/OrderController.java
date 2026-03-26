package com.accenture.pizzeria.controller.impl;

import com.accenture.pizzeria.controller.OrderApi;
import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.OrderService;
import com.accenture.pizzeria.service.dto.OrderRequestDto;
import com.accenture.pizzeria.service.dto.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Override
    public ResponseEntity<Void> addOrder(OrderRequestDto requestDto) throws PizzeriaException {
        log.info("Accessing ENDPOINT : POST /orders");
        OrderResponseDto responseDto = orderService.addOrder(requestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
