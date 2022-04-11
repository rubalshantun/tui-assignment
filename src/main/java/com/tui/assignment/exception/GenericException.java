package com.tui.assignment.exception;

public class GenericException extends RuntimeException {
    public GenericException() {
        super();
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericException(String message) {
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

}
