package com.accenture.pizzeria.controller.impl;

import com.accenture.pizzeria.controller.IngredientApi;
import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.IngredientService;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@Slf4j
public class IngredientController implements IngredientApi {
    private IngredientService ingredientService;

    @Override
    public ResponseEntity<IngredientResponseDto> addIngredient(@Valid IngredientRequestDto requestDto) throws PizzeriaException {
        log.info("Accessing ENDPOINT : POST /ingredients");
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.addIngredient(requestDto));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> getIngredientById(UUID idIngredient) throws PizzeriaException {
        log.info("Accessing ENDPOINT : GET /customers/{}",idIngredient);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.findById(idIngredient));
    }

    @Override
    public ResponseEntity<IngredientResponseDto> getIngredientByName(String name) throws PizzeriaException {
        log.info("Accessing ENDPOINT : GET /customers/name/{}",name);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.findByName(name));
    }

    @Override
    public ResponseEntity<List<IngredientResponseDto>> getAll() {
        log.info("Accessing ENDPOINT : GET /customers");
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.findAll());
    }
}
