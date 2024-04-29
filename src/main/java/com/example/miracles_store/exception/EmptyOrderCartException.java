package com.example.miracles_store.exception;

public class EmptyOrderCartException extends RuntimeException {
    public EmptyOrderCartException(String message) {
        super(message);
    }
}
