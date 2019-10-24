package com.ait.auth.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super(String.format("Invalid credentials!"));
    }
}
