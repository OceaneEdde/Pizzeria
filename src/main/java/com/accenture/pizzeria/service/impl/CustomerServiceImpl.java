package com.accenture.pizzeria.service.impl;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.CustomerMapper;
import com.accenture.pizzeria.model.Customer;
import com.accenture.pizzeria.repository.CustomerRepository;
import com.accenture.pizzeria.service.CustomerService;
import com.accenture.pizzeria.service.dto.CustomerRequestDto;
import com.accenture.pizzeria.service.dto.CustomerResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final MessageSourceAccessor messages;


    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto requestDto) throws PizzeriaException {
        log.info("Accessing Service Method : addCustomer");
        verify(requestDto);
        Customer saved = customerRepository.save(customerMapper.toEntity(requestDto));
        return customerMapper.toResponseDto(saved);
    }

    @Override
    public void verify(CustomerRequestDto requestDto) throws PizzeriaException {
        log.info("Accessing Service Method : verify");
        if(null==requestDto)
            throw new PizzeriaException(messages.getMessage("dto.null"), HttpStatus.BAD_REQUEST);
    }


}
