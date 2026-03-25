package com.accenture.pizzeria.service;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.IngredientMapper;
import com.accenture.pizzeria.model.Ingredient;
import com.accenture.pizzeria.repository.IngredientRepository;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Assertions.assertThrows(PizzeriaException.class, () -> ingredientService.addIngredient(null), "DtoRequest should not be null");
    }

    @Test
    @DisplayName("Test to find all Ingredients")
    void testFindAll() {
        List<Ingredient> ingredientList = new ArrayList<>();
        Mockito.when(ingredientRepository.findAll()).thenReturn(ingredientList);
        List<IngredientResponseDto> ingredientResponseDtoList = ingredientService.findAll();
        Assertions.assertNotNull(ingredientResponseDtoList);
    }

    @Test
    @DisplayName("Test to find Ingredient by Id success")
    void testfindByIdSuccess() throws PizzeriaException {
        UUID id = UUID.randomUUID();
        IngredientResponseDto expectedResponseDto = new IngredientResponseDto("Tomate", 0);
        Optional<Ingredient> ingredientEntity = Optional.of(new Ingredient(id, "Tomate", 0));

        Mockito.when(ingredientRepository.findById(Mockito.any(UUID.class))).thenReturn(ingredientEntity);
        Mockito.when(ingredientMapper.toIngredientResponseDto(Mockito.any(Ingredient.class))).thenReturn(expectedResponseDto);

        IngredientResponseDto actualResponseDto = ingredientService.findById(id);

        Assertions.assertAll(() -> {
            Assertions.assertNotNull(actualResponseDto, "DtoReponse should not be null");
            Assertions.assertEquals(expectedResponseDto, actualResponseDto, "expected and actual not equals");
            Assertions.assertNotNull(actualResponseDto.name(), "DtoResponse name should not be null");
            Assertions.assertEquals(expectedResponseDto.name(), actualResponseDto.name(), "expected name and actual name not equals");
            Assertions.assertEquals(expectedResponseDto.stock(), actualResponseDto.stock(), "expected stock and actual stock not equals");
        });
    }

    @Test
    @DisplayName("Test to find Ingredient by Id fail not found")
    void testfindByIdFailNotFound() {
        Mockito.when(ingredientRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> ingredientService.findById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Test to find Ingredient by Id fail Id null")
    void testfindByIdFailIdNull() {
        Assertions.assertThrows(PizzeriaException.class, () -> ingredientService.findById(null), "id.null");
    }

    @Test
    @DisplayName("Test to find Ingredient by Name success")
    void testfindByNameSuccess() throws PizzeriaException {
        UUID id = UUID.randomUUID();
        String name = "Tomate";
        IngredientResponseDto expectedResponseDto = new IngredientResponseDto(name, 0);
        Optional<Ingredient> ingredientEntity = Optional.of(new Ingredient(id, name, 0));

        Mockito.when(ingredientRepository.findByName(Mockito.any(String.class))).thenReturn(ingredientEntity);
        Mockito.when(ingredientMapper.toIngredientResponseDto(Mockito.any(Ingredient.class))).thenReturn(expectedResponseDto);

        IngredientResponseDto actualResponseDto = ingredientService.findByName(name);

        Assertions.assertAll(() -> {
            Assertions.assertNotNull(actualResponseDto, "DtoReponse should not be null");
            Assertions.assertEquals(expectedResponseDto, actualResponseDto, "expected and actual not equals");
            Assertions.assertNotNull(actualResponseDto.name(), "DtoResponse name should not be null");
            Assertions.assertEquals(expectedResponseDto.name(), actualResponseDto.name(), "expected name and actual name not equals");
            Assertions.assertEquals(expectedResponseDto.stock(), actualResponseDto.stock(), "expected stock and actual stock not equals");
        });
    }

    @Test
    @DisplayName("Test to find Ingredient by Name fail not found")
    void testfindByNameFailNotFound() {
        String name = "Tomate";
        Mockito.when(ingredientRepository.findByName(Mockito.any(String.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> ingredientService.findByName(name));
    }

    @Test
    @DisplayName("Test to find Ingredient by Name fail not found")
    void testfindByNameFailNameNull() {
        Assertions.assertThrows(PizzeriaException.class, () -> ingredientService.findByName(null));
    }

    @Test
    @DisplayName("Test if the update ingredient is correctly modified")
    void testUpdateIngredientOk() {
        UUID id = UUID.randomUUID();
        Ingredient ingredient = new Ingredient(id, "Tomate", 0);

        IngredientResponseDto dto = new IngredientResponseDto("Tomate", 5);
        IngredientResponseDto expected = new IngredientResponseDto("Tomate", 5);

        Mockito.when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));
        Mockito.when(ingredientRepository.save(Mockito.any(Ingredient.class))).thenReturn(ingredient);
        Mockito.when(ingredientMapper.toIngredientResponseDto(Mockito.any(Ingredient.class))).thenReturn(expected);

        IngredientResponseDto actual = ingredientService.updateIngredient(id, dto);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(expected, actual, "expected and actual not equals");
            Assertions.assertEquals(expected.name(), actual.name(), "expected name and actual name not equals");
            Assertions.assertEquals(expected.stock(), actual.stock(), "expected stock and actual stock not equals");
        });
    }

    @Test
    @DisplayName("Should update ingredient even if initial name is null")
    void testUpdateIngredientNameNull() {
        UUID id = UUID.randomUUID();
        Ingredient ingredient = new Ingredient(id, null, 0);

        IngredientResponseDto dto = new IngredientResponseDto("Tomate", 5);
        IngredientResponseDto expected = new IngredientResponseDto("Tomate", 5);

        Mockito.when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));
        Mockito.when(ingredientRepository.save(Mockito.any(Ingredient.class))).thenReturn(ingredient);
        Mockito.when(ingredientMapper.toIngredientResponseDto(Mockito.any(Ingredient.class))).thenReturn(expected);

        IngredientResponseDto actual = ingredientService.updateIngredient(id, dto);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(expected, actual, "expected and actual not equals");
            Assertions.assertEquals(expected.name(), actual.name(), "expected name and actual name not equals");
            Assertions.assertEquals(expected.stock(), actual.stock(), "expected stock and actual stock not equals");
        });
    }


    @Test
    @DisplayName("Should update ingredient even if initial stock is null")
    void testUpdateIngredientstockNull() {
        UUID id = UUID.randomUUID();
        Ingredient ingredient = new Ingredient(id, "Tomate", null);

        IngredientResponseDto dto = new IngredientResponseDto("Tomate", 5);
        IngredientResponseDto expected = new IngredientResponseDto("Tomate", 5);

        Mockito.when(ingredientRepository.findById(id)).thenReturn(Optional.of(ingredient));
        Mockito.when(ingredientRepository.save(Mockito.any(Ingredient.class))).thenReturn(ingredient);
        Mockito.when(ingredientMapper.toIngredientResponseDto(Mockito.any(Ingredient.class))).thenReturn(expected);

        IngredientResponseDto actual = ingredientService.updateIngredient(id, dto);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(expected, actual, "expected and actual not equals");
            Assertions.assertEquals(expected.name(), actual.name(), "expected name and actual name not equals");
            Assertions.assertEquals(expected.stock(), actual.stock(), "expected stock and actual stock not equals");
        });
    }
}
