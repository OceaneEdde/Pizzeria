package com.accenture.pizzeria.mapper;

import com.accenture.pizzeria.model.Address;
import com.accenture.pizzeria.service.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDto dto);

    AddressDto toDto(Address entity);
}
