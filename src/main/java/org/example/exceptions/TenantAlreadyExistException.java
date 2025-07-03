package org.example.exceptions;

public class TenantAlreadyExistException extends RuntimeException {
    public TenantAlreadyExistException(String message) {
        super(message);
    }
}
