package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class InvalidInputDateException extends AssignmentException {

    public InvalidInputDateException() {
        super(InvalidInputDateException.class.getSimpleName());
    }

    public InvalidInputDateException(String message) {
        super(InvalidInputDateException.class.getSimpleName(), message, ErrorType.USER_INPUT);
    }
}
