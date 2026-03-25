package com.accenture.pizzeria.repository;

import com.accenture.pizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PizzaRepository extends JpaRepository<Pizza, UUID> {
}
