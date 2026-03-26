package com.accenture.pizzeria.model;

import com.accenture.pizzeria.exception.PizzeriaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class OrderTest {

    @Test
    @DisplayName("Test to verify if ingredients is available")
    void testIsIngredientsAvailableOk() {
        Customer customer = new Customer(UUID.randomUUID(), "Joe", "Leblanc", "joe.leblanc@gmail.fr", new ArrayList<>(), new Address(UUID.randomUUID(), "7 rue du Test", "TestVille", "12345"), true);


        Ingredient tomato = new Ingredient(UUID.randomUUID(), "Tomato", 6);
        Ingredient cheese = new Ingredient(UUID.randomUUID(), "Cheese", 3);
        Ingredient basilic = new Ingredient(UUID.randomUUID(), "Basilic", 3);
        List<Ingredient> ingredients = List.of(tomato, cheese, basilic);

        Pizza pizza = new Pizza("Margarita", ESize.MEDIUM, ingredients, 10);

        Order order = new Order(UUID.randomUUID(), customer, List.of(pizza), EStatus.PENDING, LocalDateTime.now());

        Assertions.assertDoesNotThrow(() ->
                Assertions.assertTrue(order.ingredientsAvailable()));
    }

    @Test
    @DisplayName("Test to verify if ingredients is not available")
    void testIsIngredientsIsAvailableFalse() {
        Customer customer = new Customer(UUID.randomUUID(), "Joe", "Leblanc", "joe.leblanc@gmail.fr", new ArrayList<>(), new Address(UUID.randomUUID(), "7 rue du Test", "TestVille", "12345"), true);


        Ingredient tomato = new Ingredient(UUID.randomUUID(), "Tomato", 2);
        Ingredient cheese = new Ingredient(UUID.randomUUID(), "Cheese", 1);
        Ingredient basilic = new Ingredient(UUID.randomUUID(), "Basilic", 3);
        List<Ingredient> ingredients = List.of(tomato, cheese, basilic);

        Pizza pizza = new Pizza("Margarita", ESize.MEDIUM, ingredients, 10);

        Order order = new Order(UUID.randomUUID(), customer, List.of(pizza), EStatus.PENDING, LocalDateTime.now());

        Assertions.assertThrows(PizzeriaException.class, order::ingredientsAvailable);
    }

    @Test
    @DisplayName("Test to calcul total cost of an order")
    void testCalculTotalCostWithoutReduction() {
        Customer customer = new Customer(UUID.randomUUID(), "Joe", "Leblanc", "joe.leblanc@gmail.fr", new ArrayList<>(), new Address(UUID.randomUUID(), "7 rue du Test", "TestVille", "12345"), false);

        Ingredient tomato = new Ingredient(UUID.randomUUID(), "Tomato", 2);
        Ingredient cheese = new Ingredient(UUID.randomUUID(), "Cheese", 1);
        Ingredient basilic = new Ingredient(UUID.randomUUID(), "Basilic", 3);
        List<Ingredient> ingredients = List.of(tomato, cheese, basilic);

        Pizza pizzaSmall = new Pizza("Margarita", ESize.SMALL, ingredients, 10);
        Pizza pizzaMedium = new Pizza("Margarita", ESize.MEDIUM, ingredients, 10);
        Pizza pizzaLarge = new Pizza("Margarita", ESize.LARGE, ingredients, 10);

        Order order = new Order(UUID.randomUUID(), customer, List.of(pizzaSmall, pizzaMedium, pizzaLarge), EStatus.PENDING, LocalDateTime.now());

        double expected = pizzaSmall.basePrice + (pizzaMedium.basePrice * 1.2) + (pizzaLarge.getBasePrice() * 1.5);

        Assertions.assertEquals(expected, order.calculTotalPrice());

    }

    @Test
    @DisplayName("Test to calcul total cost of an order")
    void testCalculTotalCostWithReduction() {
        Customer customer = new Customer(UUID.randomUUID(), "Joe", "Leblanc", "joe.leblanc@gmail.fr", new ArrayList<>(), new Address(UUID.randomUUID(), "7 rue du Test", "TestVille", "12345"), true);

        Ingredient tomato = new Ingredient(UUID.randomUUID(), "Tomato", 2);
        Ingredient cheese = new Ingredient(UUID.randomUUID(), "Cheese", 1);
        Ingredient basilic = new Ingredient(UUID.randomUUID(), "Basilic", 3);
        List<Ingredient> ingredients = List.of(tomato, cheese, basilic);

        Pizza pizzaSmall = new Pizza("Margarita", ESize.SMALL, ingredients, 10);
        Pizza pizzaMedium = new Pizza("Margarita", ESize.MEDIUM, ingredients, 10);
        Pizza pizzaLarge = new Pizza("Margarita", ESize.LARGE, ingredients, 10);

        Order order = new Order(UUID.randomUUID(), customer, List.of(pizzaSmall, pizzaMedium, pizzaLarge), EStatus.PENDING, LocalDateTime.now());

        double expected = (pizzaSmall.basePrice + (pizzaMedium.basePrice * 1.2) + (pizzaLarge.getBasePrice() * 1.5)) * 0.9;

        Assertions.assertEquals(expected, order.calculTotalPrice());

    }

    @Test
    @DisplayName("Test to verify when the customer is a VIP for the total price calculation")
    void testIsCustomerVipTrue() {
        Customer customer = new Customer(UUID.randomUUID(), "Joe", "Leblanc", "joe.leblanc@gmail.fr", new ArrayList<>(), new Address(UUID.randomUUID(), "7 rue du Test", "TestVille", "12345"), true);
        Pizza pizza = new Pizza();
        Pizza pizza1 = new Pizza();

        List<Pizza> pizzas = List.of(pizza1, pizza);

        Order order = new Order(UUID.randomUUID(), customer, pizzas, EStatus.PENDING, LocalDateTime.now());

        Assertions.assertTrue(order.getCustomer().getIsVIP());
    }

    @Test
    @DisplayName("Test to verify when the customer is not a VIP for the total price calculation")
    void testIsCustomerVipFalse() {
        Customer customer = new Customer(UUID.randomUUID(), "Joe", "Leblanc", "joe.leblanc@gmail.fr", new ArrayList<>(), new Address(UUID.randomUUID(), "7 rue du Test", "TestVille", "12345"), false);
        Pizza pizza = new Pizza();
        Pizza pizza1 = new Pizza();

        List<Pizza> pizzas = List.of(pizza1, pizza);

        Order order = new Order(UUID.randomUUID(), customer, pizzas, EStatus.PENDING, LocalDateTime.now());

        Assertions.assertFalse(order.getCustomer().getIsVIP());
    }

    @Test
    @DisplayName("Test to verify the number of ingredients per pizza size")
    void testCalculIngredientsNumberBySize() {
        Customer customer = new Customer(UUID.randomUUID(), "Joe", "Leblanc", "joe.leblanc@gmail.fr", new ArrayList<>(), new Address(UUID.randomUUID(), "7 rue du Test", "TestVille", "12345"), true);

        Ingredient tomato = new Ingredient(UUID.randomUUID(), "Tomato", 2);
        Ingredient cheese = new Ingredient(UUID.randomUUID(), "Cheese", 1);
        Ingredient basilic = new Ingredient(UUID.randomUUID(), "Basilic", 3);
        List<Ingredient> ingredients = List.of(tomato, cheese, basilic);

        Pizza pizzaSmall = new Pizza("Margarita", ESize.SMALL, ingredients, 10);
        Pizza pizzaMedium = new Pizza("Margarita", ESize.MEDIUM, ingredients, 10);
        Pizza pizzaLarge = new Pizza("Margarita", ESize.LARGE, ingredients, 10);

        Order order = new Order(UUID.randomUUID(), customer, List.of(pizzaSmall, pizzaMedium, pizzaLarge), EStatus.PENDING, LocalDateTime.now());

        Assertions.assertEquals(1, order.calculIngredientsNumberBySize(pizzaSmall));
        Assertions.assertEquals(2, order.calculIngredientsNumberBySize(pizzaMedium));
        Assertions.assertEquals(3, order.calculIngredientsNumberBySize(pizzaLarge));
    }
}
