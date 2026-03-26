package com.accenture.pizzeria.repository;

import com.accenture.pizzeria.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
