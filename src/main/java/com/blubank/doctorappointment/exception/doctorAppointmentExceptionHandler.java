package com.blubank.doctorappointment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.Principal;

@ControllerAdvice
@Slf4j
public class doctorAppointmentExceptionHandler {

    @ExceptionHandler(value = {AssignmentException.class})
    public ResponseEntity<ErrorResponse> assignmentExceptionHandler(AssignmentException e, Principal principal) {
        ResponseEntity<ErrorResponse> responseEntity = null;
        String userName = "";
        ErrorResponse errorResponse = null;
        if (principal != null && principal.getName() != null) {
            userName = principal.getName();
        }

        if (e instanceof TimesheetNotFoundException) {
            errorResponse = ErrorResponse.of(e.getMessage(), "404", e.getType());
            responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } else {
            errorResponse = ErrorResponse.of(e.getMessage(), "406", e.getType());
            responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
        }
        log.error(" principal :  {} occurred : {} ", userName + e.getClass().getSimpleName(), errorResponse);


        return responseEntity;
    }
}
