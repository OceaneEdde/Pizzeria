package com.accenture.pizzeria.service.dto;

import com.accenture.pizzeria.model.EStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDto(
        UUID id,
        CustomerResponseDto customer,
        List<PizzaResponseDto> pizzas,
        EStatus status,
        Double totalPrice,
        LocalDateTime date
) {
}
