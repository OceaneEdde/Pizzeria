package com.accenture.pizzeria.controller.impl;

import com.accenture.pizzeria.controller.IngredientApi;
import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.IngredientService;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class IngredientController implements IngredientApi {
    private IngredientService ingredientService;

    @Override
    public ResponseEntity<IngredientResponseDto> addIngredient(@Valid IngredientRequestDto requestDto) throws PizzeriaException {
        ingredientService.addIngredient(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.addIngredient(requestDto));
    }
}
