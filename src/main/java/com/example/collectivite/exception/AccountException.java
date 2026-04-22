package com.example.collectivite.exception;

import com.example.collectivite.entity.AccountType;

import java.util.ArrayList;
import java.util.List;

public class AccountException extends RuntimeException {
    private final List<String> errors;

    public AccountException(List<String> errors) {
        super("Account creation error: " + errors);
        this.errors = errors;
    }

    public AccountException(String message){
        super(message);
        this.errors = List.of(message);
    }

    public List<String> getErrors() {
        return errors;
    }
}
