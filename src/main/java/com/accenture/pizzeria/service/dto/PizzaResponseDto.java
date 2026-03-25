package com.accenture.pizzeria.service.dto;

import com.accenture.pizzeria.model.ESize;
import com.accenture.pizzeria.model.Ingredient;

import java.util.List;

public record PizzaResponseDto(String name, ESize size, List<Ingredient> ingredients, double basePrice) {

}
