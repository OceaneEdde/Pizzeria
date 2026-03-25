package com.accenture.pizzeria.mapper;

import com.accenture.pizzeria.model.Pizza;
import com.accenture.pizzeria.service.dto.PizzaRequestDto;
import com.accenture.pizzeria.service.dto.PizzaResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {IngredientMapper.class})
public interface PizzaMapper {

    Pizza toPizza(PizzaRequestDto pizzaRequestDto);

    PizzaResponseDto toPizzaResponseDto(Pizza pizza);

}
