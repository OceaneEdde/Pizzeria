package com.accenture.pizzeria.controller;

import com.accenture.pizzeria.controller.advice.ErrorDto;
import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.OrderRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Orders", description = "Order Managing API")
@RequestMapping("/orders")
public interface OrderApi {
    @Operation(summary = "Add a new order")
    @ApiResponse(responseCode = "201", description = "Order created")
    @ApiResponse(responseCode = "400", description = "Invalid Order", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    ResponseEntity<Void> addOrder(@RequestBody OrderRequestDto requestDto) throws PizzeriaException;
}
