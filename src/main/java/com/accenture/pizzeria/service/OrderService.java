package com.accenture.pizzeria.service;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.OrderRequestDto;
import com.accenture.pizzeria.service.dto.OrderResponseDto;

public interface OrderService {

    OrderResponseDto addOrder(OrderRequestDto dto) throws PizzeriaException;
}
