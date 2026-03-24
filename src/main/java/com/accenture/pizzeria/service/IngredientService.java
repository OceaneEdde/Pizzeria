package com.accenture.pizzeria.service;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;

public interface IngredientService {
    IngredientResponseDto addIngredient(IngredientRequestDto requestDto) throws PizzeriaException;
}
