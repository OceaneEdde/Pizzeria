package com.accenture.pizzeria.controller;

import com.accenture.pizzeria.service.IngredientService;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
class IngredientControllerEndToEndTest {
    private static final String URL = "http://localhost:";
    private static final String INGREDIENTS_ENDPOINT = "/ingredients";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    IngredientService ingredientService;

    @Test
    @DisplayName("Create an Ingredient throught Post endpoint")
    void testPostIngredientSuccess(){
        IngredientRequestDto requestDto = new IngredientRequestDto("Tomato", 1);
        ResponseEntity<IngredientResponseDto> response = restTemplate.postForEntity(URL+port+INGREDIENTS_ENDPOINT, requestDto, IngredientResponseDto.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(requestDto.name(), response.getBody().name());
        Assertions.assertEquals(requestDto.stock(), response.getBody().stock());

    }
}
