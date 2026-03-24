package com.accenture.pizzeria.mapper;

import com.accenture.pizzeria.model.Customer;
import com.accenture.pizzeria.service.dto.CustomerRequestDto;
import com.accenture.pizzeria.service.dto.CustomerResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface CustomerMapper {
    Customer toEntity(CustomerRequestDto dto);

    CustomerResponseDto toResponseDto(Customer entity);
}
