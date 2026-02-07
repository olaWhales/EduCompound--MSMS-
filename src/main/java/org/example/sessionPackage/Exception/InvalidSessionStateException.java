package org.example.sessionPackage.Exception;

public class InvalidSessionStateException extends RuntimeException {
    public InvalidSessionStateException(String message) {
        super(message);
    }
}
