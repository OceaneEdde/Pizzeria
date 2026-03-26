package com.accenture.pizzeria.model;

import com.accenture.pizzeria.exception.PizzeriaException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Customer customer;

    @ManyToMany
    private List<Pizza> pizzas;

    private EStatus status;

    private Double totalPrice = 0.0;

    private LocalDateTime date;

    public Order(UUID id, Customer customer, List<Pizza> pizzas, EStatus status, LocalDateTime date) {
        this.id = id;
        this.customer = customer;
        this.pizzas = pizzas;
        this.status = status;
        this.date = date;
    }


    /**
     * Method to verify if an Ingredient have an available stock, if not the Pizza cannot be created
     *
     * @return true OR false
     */
    public boolean ingredientsAvailable() throws PizzeriaException {
        for (Pizza pizza : pizzas) {
            int numberIngredientNeeded = calculIngredientsNumberBySize(pizza);

            for (Ingredient ingredient : pizza.getIngredients()) {
                if (ingredient.getStock() < numberIngredientNeeded) {
                    throw new PizzeriaException("pizza.ingredients.notavailable", HttpStatus.BAD_REQUEST);
                }
                ingredient.setStock(ingredient.getStock() - numberIngredientNeeded);
            }
        }
        return true;
    }

    /**
     * Calcul the quantity of Ingredient'stock for a pizza by size
     *
     * @return The quantity of ingredients remove from the Ingredient stock
     */
    protected int calculIngredientsNumberBySize(Pizza pizza) {
        int returnedValue = 0;
        switch (pizza.size) {
            case SMALL -> returnedValue = 1;
            case MEDIUM -> returnedValue = 2;
            case LARGE -> returnedValue = 3;
        }
        return returnedValue;
    }

    /**
     * Calcul the total price of an Order based and the different pizza's size
     *
     * @return double totalPrince (of the order)
     */
    public double calculTotalPrice() {

        final double MEDIUM = 1.2;
        final double LARGE = 1.5;
        final double REDUCTION = 0.9;

        this.pizzas.forEach(pizza -> {
            switch (pizza.size) {
                case SMALL -> totalPrice += pizza.basePrice;
                case MEDIUM -> totalPrice += pizza.basePrice * MEDIUM;
                case LARGE -> totalPrice += pizza.basePrice * LARGE;
            }
        });

        if (this.customer.getIsVIP())
            totalPrice *= REDUCTION;

        return totalPrice;
    }
}
