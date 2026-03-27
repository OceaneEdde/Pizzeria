package com.accenture.pizzeria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * An Entity for the ingredients of a pizza
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    private Integer stock;

    public Ingredient(String name, Integer stock) {
        this.name = name;
        this.stock = stock;
    }

    public Ingredient(String name) {
        this.name = name;
    }
}
