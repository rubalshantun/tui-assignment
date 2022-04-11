package com.tui.assignment.exception;

/**
 * Custom exception in response to a request with username which doesn't
 * exits on GitHub .
 */

public class UserDoesNotExitsException extends RuntimeException {

    public UserDoesNotExitsException() {
        super();
    }

    public UserDoesNotExitsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesNotExitsException(String message) {
        super(message);
    }

    public UserDoesNotExitsException(Throwable cause) {
        super(cause);
    }

}
