package com.accenture.pizzeria.controller.impl;

import com.accenture.pizzeria.mapper.IngredientMapper;
import com.accenture.pizzeria.service.dto.IngredientRequestDto;
import com.accenture.pizzeria.service.dto.IngredientResponseDto;
import com.accenture.pizzeria.service.impl.IngredientServiceImpl;
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
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = IngredientController.class)
class IngredientControllerIntegrationTest {

    private static final String API_INGREDIENTS_ENDPOINT = "/ingredients";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientServiceImpl ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
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
                        .with(csrf())
                        .with(user("test").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("Test to find an Ingredient by it's Id in the database")
    void testFindByIdSuccess() throws Exception {
        String name = "Tomato";
        Integer stock = 1;
        UUID id = UUID.randomUUID();
        IngredientResponseDto responseDto = new IngredientResponseDto(name, stock);

        doReturn(responseDto).when(ingredientService).findById(id);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s%s%s", API_INGREDIENTS_ENDPOINT, "/", id))
                        .with(csrf())
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Test to find an Ingredient by it's Name in the database")
    void testFindByNameSuccess() throws Exception {
        String name = "Tomato";
        Integer stock = 1;
        IngredientResponseDto responseDto = new IngredientResponseDto(name, stock);

        doReturn(responseDto).when(ingredientService).findByName(name);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s%s%s", API_INGREDIENTS_ENDPOINT, "/name/", name))
                        .with(csrf())
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test to find all the ingredients in the database")
    void testFindAllSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_INGREDIENTS_ENDPOINT)
                        .with(csrf())
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk());
    }
}
