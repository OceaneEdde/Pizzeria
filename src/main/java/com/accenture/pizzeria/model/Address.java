package com.accenture.pizzeria.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private UUID id;
    private String street;
    private String city;
    private String postalCode;
}
