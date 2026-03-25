package com.accenture.pizzeria.model;

import com.accenture.pizzeria.exception.PizzeriaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PizzaTest {


    @Test
    @DisplayName("Test if the pizza contains minimum one Ingredient")
    void doesPizzaHasIngredientsOk() {
        Ingredient ingredient = new Ingredient();
        List<Ingredient> ingredients = List.of(ingredient);
        Pizza pizza = new Pizza("Margarita",ESize.MEDIUM, ingredients,15);

        Assertions.assertDoesNotThrow(pizza::doesPizzaHasIngredients);
        Assertions.assertFalse(pizza.ingredients.isEmpty());
    }

    @Test
    @DisplayName("Test if the pizza return exception because not have ingredient")
    void testDoesPizzaHasIngredientsShouldThrowException() {
        List<Ingredient> ingredients = new ArrayList<>();
        Pizza pizza = new Pizza("margarita", ESize.MEDIUM, ingredients,15);
        Assertions.assertThrows(PizzeriaException.class, pizza::doesPizzaHasIngredients);
    }


    @Test
    @DisplayName("Test if the price of pizza is greater than Zero")
    void testPricePizzaGreaterThanZero() {
        Ingredient ingredient = new Ingredient();
        List<Ingredient> ingredients = List.of(ingredient);
        Pizza pizza = new Pizza("margarita", ESize.MEDIUM, ingredients,15);
        Assertions.assertDoesNotThrow(pizza::doesPricePizzaGreaterThanZero);
    }

    @Test
    @DisplayName("Test if the basePrice return exception because is less than Zero")
    void testPricePizzaLessThanZero() {
        Ingredient ingredient = new Ingredient();
        List<Ingredient> ingredients = List.of(ingredient);
        Pizza pizza = new Pizza("margarita", ESize.MEDIUM, ingredients,0);
        Assertions.assertThrows(PizzeriaException.class, pizza::doesPricePizzaGreaterThanZero);
    }
}
