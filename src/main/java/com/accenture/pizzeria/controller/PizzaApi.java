package com.accenture.pizzeria.controller;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.PizzaRequestDto;
import com.accenture.pizzeria.service.dto.PizzaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name= "pizza", description = "Api to manage pizza")
@RequestMapping("/pizzas")
public interface PizzaApi {

    @Operation(summary = "Add new pizza")
    @ApiResponse(responseCode = "201", description = "Created Pizza" )
    @ApiResponse(responseCode = "400", description = "Invalid Pizza")
    @PostMapping
    ResponseEntity<PizzaResponseDto> addPizza(@RequestBody PizzaRequestDto requestdto) throws PizzeriaException;
}
