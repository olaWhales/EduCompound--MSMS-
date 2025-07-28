package org.example.exceptions;

public class ParentAlreadyRegisteredException extends RuntimeException {
    public ParentAlreadyRegisteredException(String message) {
        super(message);
    }
}
