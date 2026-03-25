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

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class IngredientController implements IngredientApi {
    private IngredientService ingredientService;

    @Override
    public ResponseEntity<IngredientResponseDto> addIngredient(@Valid IngredientRequestDto requestDto) throws PizzeriaException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.addIngredient(requestDto));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> getIngredientById(UUID idIngredient) throws PizzeriaException {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.findById(idIngredient));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> getIngredientByName(String name) throws PizzeriaException {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.findByName(name));
    }

    @Override
    public ResponseEntity<List<IngredientResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.findAll());
    }
}
