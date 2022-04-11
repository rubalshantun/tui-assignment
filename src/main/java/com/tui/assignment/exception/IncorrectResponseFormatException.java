package com.tui.assignment.exception;

/**
 * Custom Exception class in response to getting not supported "accept"
 * header e.x. "application/xml" in request
 */
public class IncorrectResponseFormatException extends RuntimeException {
    public IncorrectResponseFormatException() {
        super();
    }

    public IncorrectResponseFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectResponseFormatException(String message) {
        super(message);
    }

    public IncorrectResponseFormatException(Throwable cause) {
        super(cause);
    }
}
