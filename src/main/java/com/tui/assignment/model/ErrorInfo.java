package com.tui.assignment.model;


/**
 * POJO class to hold the custom Exception/error info which
 * we are going to send in JSON Response
 */

public class ErrorInfo {
    private int status;
    private String message;

    public ErrorInfo(int code, String detail) {
        status = code;
        message = detail;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "status : " + getStatus() + ", message : " + getMessage();
    }
}
