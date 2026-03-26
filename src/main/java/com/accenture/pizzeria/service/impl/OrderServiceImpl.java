package com.accenture.pizzeria.service.impl;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.CustomerMapper;
import com.accenture.pizzeria.mapper.OrderMapper;
import com.accenture.pizzeria.model.EStatus;
import com.accenture.pizzeria.model.Order;
import com.accenture.pizzeria.repository.CustomerRepository;
import com.accenture.pizzeria.repository.OrderRepository;
import com.accenture.pizzeria.service.OrderService;
import com.accenture.pizzeria.service.dto.OrderRequestDto;
import com.accenture.pizzeria.service.dto.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Override
    public OrderResponseDto addOrder(OrderRequestDto dto) throws PizzeriaException {
        if (dto == null)
            throw new PizzeriaException("order.dto.null", HttpStatus.BAD_REQUEST);

        Order order = orderMapper.toOrder(dto);

        order.ingredientsAvailable();
        order.calculTotalPrice();



        order.setStatus(EStatus.PENDING);
        order.setDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);

        return orderMapper.toOrderResponseDto(saved);
    }
}
