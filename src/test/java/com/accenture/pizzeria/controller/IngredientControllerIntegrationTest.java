package com.accenture.pizzeria.controller;

import com.accenture.pizzeria.controller.impl.IngredientController;
import com.accenture.pizzeria.mapper.IngredientMapper;
import com.accenture.pizzeria.service.IngredientServiceImpl;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
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
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

@WebMvcTest(controllers = IngredientController.class)
class IngredientControllerIntegrationTest {

    private static final String API_INGREDIENTS_ENDPOINT = "/ingredients";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientServiceImpl ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Test
    @DisplayName("Test to persist Ingredient into the database")
    void testPersistIngredientSuccess() throws Exception {
        String name = "Tomato";
        Integer stock = 1;
        IngredientRequestDto requestDto = new IngredientRequestDto(name, stock);
        IngredientResponseDto responseDto = new IngredientResponseDto(name, stock);

        Mockito.when(ingredientService.addIngredient(Mockito.any(IngredientRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_INGREDIENTS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
}
