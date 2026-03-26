package com.accenture.pizzeria.service.dto;

import java.util.List;

public record OrderRequestDto(
        String email,
        List<PizzaRequestDto> pizzas
) {
}
