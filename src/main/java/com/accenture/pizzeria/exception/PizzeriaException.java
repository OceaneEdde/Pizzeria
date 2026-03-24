package com.accenture.pizzeria.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class PizzeriaException extends Exception {
    private final HttpStatusCode code;

    public PizzeriaException(String message, HttpStatusCode code) {
        super(message);
        this.code=code;
    }

}