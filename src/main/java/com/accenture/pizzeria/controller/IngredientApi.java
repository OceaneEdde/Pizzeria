package com.accenture.pizzeria.controller;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Ingredients", description = "API to manage Ingredients")
@RequestMapping("/ingredients")
public interface IngredientApi {

    @Operation(summary = "Add a new Ingredient")
    @ApiResponse(responseCode = "201", description = "Ingredient created")
    @ApiResponse(responseCode = "400", description = "Invalid request") // TODO ajouter le schema avec ErrorDto.class
    @PostMapping
    ResponseEntity<IngredientResponseDto> addIngredient(@RequestBody IngredientRequestDto requestDto) throws PizzeriaException;
}
