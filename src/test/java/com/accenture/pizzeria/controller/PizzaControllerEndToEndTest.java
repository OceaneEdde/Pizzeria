package com.accenture.pizzeria.controller;


import com.accenture.pizzeria.exception.PizzeriaException;
import com.accenture.pizzeria.model.ESize;
import com.accenture.pizzeria.model.Ingredient;
import com.accenture.pizzeria.service.PizzaServiceImpl;
import com.accenture.pizzeria.service.dto.PizzaRequestDto;
import com.accenture.pizzeria.service.dto.PizzaResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@ActiveProfiles
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PizzaControllerEndToEndTest {
    private static final String URL = "http://localhost:";
    private static final String ENDPOINT_PIZZA = "/pizzas";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PizzaServiceImpl pizzaService;


    @Test
    @Order(1)
    void testPostPizzaSuccess() {
        String name = "Pizza";
        ESize size = ESize.SMALL;
        Ingredient ingredient = new Ingredient("Tomato", 1);
        List<Ingredient> ingredients = List.of(ingredient);
        double basePrice = 10;

        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto(name, size, ingredients, basePrice);
        ResponseEntity<PizzaResponseDto> response = restTemplate.postForEntity(URL + port + ENDPOINT_PIZZA, pizzaRequestDto, PizzaResponseDto.class);


        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Post pizza must return a 201 http response code.");
            Assertions.assertNotNull(response.getBody(), "Pizza must not be null");
            Assertions.assertNotNull(response.getBody().name(), "Pizza name must not be null");
            Assertions.assertEquals(size, response.getBody().size(), "Pizza must have the same size");
            Assertions.assertEquals(ingredients, response.getBody().ingredients(), "Pizza must have the same ingredients");
            Assertions.assertEquals(pizzaRequestDto.basePrice(), response.getBody().basePrice(), "Pizza must have the same basePrice");
        });
    }
}

