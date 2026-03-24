package com.accenture.pizzeria.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CustomerRequestDto(
        @NotNull(message = "customer.firstName.null") @NotBlank(message = "customer.firstName.blank") String firstName,
        @NotNull(message = "customer.lastName.null") @NotBlank(message = "customer.lastName.blank") String lastName,
        @NotNull(message = "customer.email.null") @NotBlank(message = "customer.email.blank") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "customer.email.invalid") String email,
        @NotNull(message = "customer.address.null") @Valid AddressDto address
) {
}
