package com.accenture.pizzeria.service;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.IngredientPatchRequestDto;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;

import java.util.List;
import java.util.UUID;

public interface IngredientService {
    IngredientResponseDto addIngredient(IngredientRequestDto requestDto) throws PizzeriaException;

    List<IngredientResponseDto> findAll();

    IngredientResponseDto findById(UUID id) throws PizzeriaException;

    IngredientResponseDto findByName(String name) throws PizzeriaException;

    IngredientResponseDto updateIngredient(String name, IngredientPatchRequestDto patchRequestDto);
}
