package com.accenture.pizzeria.exception;

import org.springframework.http.HttpStatusCode;

public class PizzeriaException extends Exception {
    private HttpStatusCode code;

    public PizzeriaException(String message, HttpStatusCode code) {
        super(message);
        this.code = code;
    }

    public HttpStatusCode getCode() {
        return code;
    }
}
