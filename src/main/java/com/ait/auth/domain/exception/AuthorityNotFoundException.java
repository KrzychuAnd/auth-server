package com.ait.auth.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AuthorityNotFoundException extends RuntimeException {

    public AuthorityNotFoundException(String authority) {
        super(String.format("Authority '%s' not found!", authority));
    }
}
