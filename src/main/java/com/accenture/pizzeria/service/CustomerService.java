package com.accenture.pizzeria.service;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.CustomerRequestDto;
import com.accenture.pizzeria.service.dto.CustomerResponseDto;

public interface CustomerService {

    CustomerResponseDto addCustomer(CustomerRequestDto requestDto) throws PizzeriaException;
    void verify(CustomerRequestDto requestDto) throws PizzeriaException;

    CustomerResponseDto findByEmail(String email) throws PizzeriaException;
}
