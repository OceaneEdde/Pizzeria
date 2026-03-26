package com.accenture.pizzeria.controller.impl;

import com.accenture.pizzeria.controller.PizzaApi;
import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.PizzaService;
import com.accenture.pizzeria.service.dto.PizzaRequestDto;
import com.accenture.pizzeria.service.dto.PizzaResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class PizzaController implements PizzaApi {

    private final PizzaService pizzaService;

    @Override
    public ResponseEntity<PizzaResponseDto>addPizza(@Valid PizzaRequestDto requestDto) throws PizzeriaException {
        log.info("Accessing ENDPOINT : POST /pizza");
        return ResponseEntity.status(HttpStatus.CREATED).body(pizzaService.addPizza(requestDto));

    }
}
