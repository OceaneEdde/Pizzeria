package com.accenture.pizzeria.controller.impl;

import com.accenture.pizzeria.service.IngredientService;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IngredientControllerEndToEndTest {
    private static final String URL = "http://localhost:";
    private static final String INGREDIENTS_ENDPOINT = "/ingredients";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IngredientService ingredientService;

    @Test
    @DisplayName("Create an Ingredient throught Post endpoint")
    @Order(1)
    void testPostIngredientSuccess() {
        IngredientRequestDto requestDto = new IngredientRequestDto("Tomato", 1);
        ResponseEntity<IngredientResponseDto> response = restTemplate.postForEntity(URL + port + INGREDIENTS_ENDPOINT, requestDto, IngredientResponseDto.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(requestDto.name(), response.getBody().name());
        Assertions.assertEquals(requestDto.stock(), response.getBody().stock());

    }

    @Test
    @DisplayName("Find an Ingredient by it's name through GET endpoint")
    void testGetIngredientByNameSuccess() {
        String name = "Tomato";
        IngredientResponseDto expectedResponseDto = new IngredientResponseDto(name, 1);

        ResponseEntity<IngredientResponseDto> response = restTemplate.exchange(URL + port + INGREDIENTS_ENDPOINT + "/name/" + name, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(expectedResponseDto.name(), response.getBody().name());
        Assertions.assertEquals(expectedResponseDto.stock(), response.getBody().stock());

    }
}
