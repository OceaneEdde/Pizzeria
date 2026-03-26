package com.accenture.pizzeria.controller;

import com.accenture.pizzeria.controller.advice.ErrorDto;
import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.CustomerRequestDto;
import com.accenture.pizzeria.service.dto.CustomerResponseDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customers", description = "Customer Managing API")
@RequestMapping("/customers")
public interface CustomerApi {
    @Operation(summary = "Add a new customer")
    @ApiResponse(responseCode = "201", description = "Customer created")
    @ApiResponse(responseCode = "400", description = "Invalid Customer", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    ResponseEntity<Void> addCustomer(@RequestBody CustomerRequestDto requestDto) throws PizzeriaException;

    @Operation(summary = "Find Customer By Email")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/email/{email}")
    ResponseEntity<CustomerResponseDto> getCustomerByEmail(@Parameter(description = "Customer email not found", required = true) @PathVariable("email") String email) throws PizzeriaException;

}
