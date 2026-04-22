package com.example.collectivite.exception;

public class BadReqestException extends RuntimeException {
    private final String field;
    public BadReqestException(String message) {
        super(message);
        this.field = null;
    }

    public BadReqestException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
