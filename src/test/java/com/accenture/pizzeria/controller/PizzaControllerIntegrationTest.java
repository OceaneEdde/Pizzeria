package com.accenture.pizzeria.controller;

import com.accenture.pizzeria.controller.impl.PizzaController;
import com.accenture.pizzeria.mapper.PizzaMapper;
import com.accenture.pizzeria.model.ESize;
import com.accenture.pizzeria.model.Ingredient;
import com.accenture.pizzeria.service.PizzaServiceImpl;
import com.accenture.pizzeria.service.dto.PizzaRequestDto;
import com.accenture.pizzeria.service.dto.PizzaResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

@WebMvcTest(controllers = PizzaController.class)
class PizzaControllerIntegrationTest {

    private static final String API_PIZZA_ENDPOINT = "/pizzas";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PizzaServiceImpl pizzaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PizzaMapper pizzaMapper;

    @Test
    @DisplayName("Test to persist Pizza into the database")
    void testPersistPizzaSuccess() throws Exception {

        String name = "Pizza";
        ESize size = ESize.SMALL;
        Ingredient ingredient = new Ingredient("Tomato", 1);
        List<Ingredient> ingredients = List.of(ingredient);
        double basePrice = 10;

        PizzaRequestDto dtoRequest = new PizzaRequestDto(name, size, ingredients, basePrice);
        PizzaResponseDto responseDto = new PizzaResponseDto(name, size, ingredients, basePrice);

        Mockito.when(pizzaService.addPizza(Mockito.any(PizzaRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PIZZA_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(dtoRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
