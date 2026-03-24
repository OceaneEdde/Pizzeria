package com.accenture.pizzeria.mapper;

import com.accenture.pizzeria.model.Ingredient;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient toIngredient(IngredientRequestDto ingredientRequestDto);
    IngredientResponseDto toIngredientResponseDto(Ingredient ingredient);
}
