package com.accenture.pizzeria.service.impl;

import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.mapper.PizzaMapper;
import com.accenture.pizzeria.model.ESize;
import com.accenture.pizzeria.model.Ingredient;
import com.accenture.pizzeria.model.Pizza;
import com.accenture.pizzeria.repository.PizzaRepository;
import com.accenture.pizzeria.service.PizzaService;
import com.accenture.pizzeria.service.dto.PizzaRequestDto;
import com.accenture.pizzeria.service.dto.PizzaResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;


import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PizzaServiceImplTest {

    @Mock
    private PizzaRepository  pizzaRepository;
    @Mock
    private PizzaMapper pizzaMapper;
    @Mock
    private MessageSourceAccessor messages;
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        pizzaRepository = mock(PizzaRepository.class);
        pizzaMapper = mock(PizzaMapper.class);
        pizzaService = new PizzaServiceImpl(pizzaRepository, pizzaMapper, messages);
    }

    @Test
    void testAddPizzaOK() throws PizzeriaException {
        Ingredient ingredient = mock(Ingredient.class);

        String name = "Pizza";
        ESize size = ESize.SMALL;
        List<Ingredient> ingredients = List.of(ingredient);

        PizzaRequestDto dtoRequest = new PizzaRequestDto("Margarita", ESize.SMALL, ingredients, 10.0);
        PizzaResponseDto returnedResponse = new PizzaResponseDto("Margarita",ESize.SMALL, ingredients, 10.0);
        Pizza pizzaEntity = new Pizza(name, size, ingredients,10.0);
        Pizza spy = Mockito.spy(pizzaEntity);

        Mockito.when(pizzaMapper.toPizza(Mockito.any(PizzaRequestDto.class))).thenReturn(spy);
        Mockito.when(pizzaRepository.save(Mockito.any(Pizza.class))).thenReturn(spy);
        Mockito.when(pizzaMapper.toPizzaResponseDto(Mockito.any(Pizza.class))).thenReturn(returnedResponse);

        PizzaResponseDto actualResponse = pizzaService.addPizza(dtoRequest);

        Assertions.assertAll(() ->{
            Assertions.assertNotNull(actualResponse, "PizzaResponseDto should not be null");
            Assertions.assertEquals(returnedResponse, actualResponse, "expected and actual not equals");
            Assertions.assertNotNull(actualResponse.name(), "PizzaResponseDto name should not be null");
            Assertions.assertEquals(returnedResponse.name(), actualResponse.name(), "expected name and actual name not equals");
            Assertions.assertNotNull(actualResponse.size(), "PizzaResponseDto size should not be null");
            Assertions.assertNotNull(actualResponse.ingredients(), "PizzaResponseDto ingredients should not be null");
            Assertions.assertFalse(actualResponse.ingredients().isEmpty(), "PizzaResponseDto ingredients should not be empty");
            Assertions.assertEquals(returnedResponse.basePrice(), actualResponse.basePrice(), "expected and actual not equals");
        });

        Mockito.verify(spy, Mockito.times(1)).doesPizzaHasIngredients();

    }



}
