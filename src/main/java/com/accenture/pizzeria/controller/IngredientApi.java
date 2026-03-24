package com.accenture.pizzeria.controller;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IngredientApi {

    @Operation(summary = "Add a new Ingredient")
    @ApiResponse(responseCode = "201", description = "Ingredient created")
    @ApiResponse(responseCode = "400", description = "Invalid request") // TODO ajouter le schema avec ErrorDto.class
    @PostMapping
    ResponseEntity<Void> addIngredient(@RequestBody IngredientRequestDto requestDto) throws PizzeriaException;
}
