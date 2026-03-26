package com.accenture.pizzeria.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequestDto(
        @NotNull(message = "ingredient.name.null")
        @NotBlank (message = "ingredient.name.notblank")
        String name,
        @Min(0) Integer stock) {
}
