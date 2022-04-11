package com.tui.assignment.controller;


import com.tui.assignment.exception.GenericException;
import com.tui.assignment.exception.IncorrectResponseFormatException;
import com.tui.assignment.exception.UserDoesNotExitsException;
import com.tui.assignment.model.ErrorInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 *  This class contains mapping of all the custom exception to their desired handling to provide
 *  graceful response
 */

@ControllerAdvice
public class ErrorHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserDoesNotExitsException.class)
    public
    @ResponseBody
    ErrorInfo handleUserDoesNotExitsException(UserDoesNotExitsException ex) {
        log.info("Converting  UserDoesNotExitsException to RestResponse : " + ex.getMessage());
        return new ErrorInfo(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(IncorrectResponseFormatException.class)
    public
    @ResponseBody()
    ResponseEntity<byte[]> handleIncorrectResponseFormatException(IncorrectResponseFormatException ex) {
        log.info("Converting  IncorrectResponseFormatException to RestResponse : " + ex.getMessage());
        ErrorInfo ob = new ErrorInfo(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage());
        try {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(objectMapper.writeValueAsBytes(ob));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ob.toString().getBytes(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
        }
    }



     // this is to handle highly unlikely event of a Runtime Exception in Service Layer class which is not handled above
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GenericException.class)
    public
    @ResponseBody
    ErrorInfo handleGenericException(GenericException ex) {
        log.info("Converting  GenericException to RestResponse : " + ex.getMessage());
        return new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }

}
