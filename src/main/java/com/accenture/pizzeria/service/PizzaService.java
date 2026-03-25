package com.accenture.pizzeria.service;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.PizzaRequestDto;
import com.accenture.pizzeria.service.dto.PizzaResponseDto;

public interface PizzaService {

    PizzaResponseDto addPizza(PizzaRequestDto pizzaRequestDto) throws PizzeriaException;
}
