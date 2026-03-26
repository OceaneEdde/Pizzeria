package com.accenture.pizzeria.service.dto;

import jakarta.validation.constraints.Min;

public record IngredientPatchRequestDto(
       String name,
        @Min(0)Integer stock) {
}
