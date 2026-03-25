package com.accenture.pizzeria.controller;

import com.accenture.pizzeria.controller.advice.ErrorDto;
import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Ingredients", description = "API to manage Ingredients")
@RequestMapping("/ingredients")
public interface IngredientApi {

    @Operation(summary = "Add a new Ingredient")
    @ApiResponse(responseCode = "201", description = "Ingredient created")
    @ApiResponse(responseCode = "400", description = "Invalid request",content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @PostMapping
    ResponseEntity<IngredientResponseDto> addIngredient(@RequestBody IngredientRequestDto requestDto) throws PizzeriaException;

    @Operation(summary = "Find Ingredient By Id")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Ingredient not found",content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/{id}")
    ResponseEntity<IngredientResponseDto> getIngredientById(@Parameter(description = "Ingredient id not found", required = true) @PathVariable("id") UUID idIngredient) throws PizzeriaException;

    @Operation(summary = "Find Ingredient By Name")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Ingredient not found",content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    @GetMapping("/name/{name}")
    ResponseEntity<IngredientResponseDto> getIngredientByName(@Parameter(description = "Ingredient name not found", required = true) @PathVariable("name") String name) throws PizzeriaException;

    @Operation(summary = "Find All Ingredients")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    ResponseEntity<List<IngredientResponseDto>> getAll();

}
