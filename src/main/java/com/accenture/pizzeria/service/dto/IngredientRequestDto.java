package com.accenture.pizzeria.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequestDto(
        @NotNull(message = "ingredient.name.null")
        @NotBlank (message = "ingredient.name.notblank")
        String name,
        Integer stock) {
}
