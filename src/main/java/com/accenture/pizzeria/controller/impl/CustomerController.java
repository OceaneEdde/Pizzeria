package com.accenture.pizzeria.controller.impl;

import com.accenture.pizzeria.controller.CustomerApi;
import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.CustomerService;
import com.accenture.pizzeria.service.dto.CustomerRequestDto;
import com.accenture.pizzeria.service.dto.CustomerResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<Void> addCustomer(CustomerRequestDto requestDto) throws PizzeriaException {
        log.info("Accessing ENDPOINT : POST /customers");
        CustomerResponseDto responseDto = customerService.addCustomer(requestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDto.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    public ResponseEntity<CustomerResponseDto> getCustomerByEmail(String email) throws PizzeriaException {
        log.info("Accessing ENDPOINT : GET /customers/email/{}", email);
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findByEmail(email));
    }
}
