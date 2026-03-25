package com.accenture.pizzeria.service.dto;

import com.accenture.pizzeria.model.ESize;
import com.accenture.pizzeria.model.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PizzaRequestDto (
    @NotBlank(message = "pizza.error.name")
    String name,

    @NotNull(message = "pizza.error.size")
    ESize size,

    @NotNull(message = "pizza.error.ingredients")
    List<Ingredient> ingredients,

    @NotNull(message = "pizza.error.basePrice")
    double basePrice)

{

}
