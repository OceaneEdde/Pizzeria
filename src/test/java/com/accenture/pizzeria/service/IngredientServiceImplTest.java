package com.accenture.pizzeria.service;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.IngredientMapper;
import com.accenture.pizzeria.model.Ingredient;
import com.accenture.pizzeria.repository.IngredientRepository;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {
    @Mock
    private IngredientRepository ingredientRepository;
    private IngredientService ingredientService;
    @Mock
    private IngredientMapper ingredientMapper;

    @BeforeEach
    void setup() {
        ingredientRepository = mock(IngredientRepository.class);
        ingredientMapper = mock(IngredientMapper.class);
        ingredientService = new IngredientServiceImpl(ingredientRepository, ingredientMapper);
    }

    @Test
    @DisplayName("Test to add a new Ingredient into the database")
    void testAddIngredientValidInput() throws PizzeriaException {
        IngredientRequestDto requestDto = new IngredientRequestDto("Tomate", 0);
        IngredientResponseDto expectedResponseDto = new IngredientResponseDto("Tomate", 0);
        Ingredient ingredientEntity = new Ingredient(UUID.randomUUID(), "Tomate", 0);

        Mockito.when(ingredientMapper.toIngredient(Mockito.any(IngredientRequestDto.class))).thenReturn(ingredientEntity);
        Mockito.when(ingredientRepository.save(Mockito.any(Ingredient.class))).thenReturn(ingredientEntity);
        Mockito.when(ingredientMapper.toIngredientResponseDto(Mockito.any(Ingredient.class))).thenReturn(expectedResponseDto);

        IngredientResponseDto actualResponseDto = ingredientService.addIngredient(requestDto);

        Assertions.assertAll(() -> {
            Assertions.assertNotNull(actualResponseDto, "DtoReponse should not be null");
            Assertions.assertEquals(expectedResponseDto, actualResponseDto, "expected and actual not equals");
            Assertions.assertNotNull(actualResponseDto.name(), "DtoResponse name should not be null");
            Assertions.assertEquals(expectedResponseDto.name(), actualResponseDto.name(), "expected name and actual name not equals");
            Assertions.assertEquals(expectedResponseDto.stock(), actualResponseDto.stock(), "expected stock and actual stock not equals");
        });
    }

    @Test
    @DisplayName("Test to add a new Ingredient when requestDto is null")
    void testAddIngredientNotValidInput() {
        Assertions.assertThrows(PizzeriaException.class, ()-> ingredientService.addIngredient(null), "DtoRequest should not be null");
    }
}
