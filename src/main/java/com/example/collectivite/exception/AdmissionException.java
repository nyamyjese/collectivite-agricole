package com.example.collectivite.exception;

import java.util.ArrayList;
import java.util.List;

public class AdmissionException extends RuntimeException {
    private final List<String> errors;

    public AdmissionException(List<String> errors) {
        super("Condition requirements not met : " + errors);
        this.errors = errors;
    }

    public AdmissionException(String message) {
        super(message);
        this.errors = List.of(message);
    }

    public List<String> getErrors() {
        return errors;
    }
}
