package com.accenture.pizzeria.service.dto;

import java.util.UUID;

public record CustomerResponseDto(
        UUID uuid,
        String firstName,
        String lastName,
        String email,
        // TODO : ajouter ceci : List<OrderResponseDto> orders
        AddressDto address,
        Boolean isVIP
) {
}
