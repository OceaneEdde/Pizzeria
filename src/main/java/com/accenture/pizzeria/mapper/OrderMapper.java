package com.accenture.pizzeria.mapper;

import com.accenture.pizzeria.model.Order;
import com.accenture.pizzeria.service.dto.OrderRequestDto;
import com.accenture.pizzeria.service.dto.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PizzaMapper.class})
public interface OrderMapper {

    @Mapping(target = "customer.email", ignore = true)
    Order toOrder(OrderRequestDto requestDto);

    OrderResponseDto toOrderResponseDto(Order entity);

}
