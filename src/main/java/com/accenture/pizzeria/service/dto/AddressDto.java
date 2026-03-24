package com.accenture.pizzeria.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDto(
        @NotNull(message = "address.street.null") @NotBlank(message = "address.street.blank") String street,
        @NotNull(message = "address.city.null") @NotBlank(message = "address.city.blank") String city,
        @NotNull(message = "address.postal-code.null") @NotBlank(message = "address.postal-code.blank") String postalCode
) {

}
