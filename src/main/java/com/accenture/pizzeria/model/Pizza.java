package com.accenture.pizzeria.model;


import com.accenture.pizzeria.exception.PizzeriaException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString
@NoArgsConstructor
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String name;
    ESize size;


    List<Ingredient> ingredients;
    double basePrice;

    public Pizza(String name, ESize size, List<Ingredient> ingredients, double basePrice) {
        this.name = name;
        this.size = size;
        this.ingredients = ingredients;
        this.basePrice = basePrice;
    }

    /**
     * This method checks if the pizza contains minimum 1 ingredient
     * and return exception if this is not
     * @throws PizzeriaException
     */
    public void doesPizzaHasIngredients() throws PizzeriaException {
        if (this.ingredients.isEmpty())
            throw new PizzeriaException("Pizza has no ingredients", HttpStatus.BAD_REQUEST);
    }

    /**
     * This method checks if the price of pizza is Greater than Zero
     * and return exception if this is not
     * @throws PizzeriaException
     */
    public void doesPricePizzaGreaterThanZero() throws PizzeriaException {
        if (this.basePrice <= 0)
            throw new PizzeriaException("Pizza's price is less than 0", HttpStatus.BAD_REQUEST);
    }
}
