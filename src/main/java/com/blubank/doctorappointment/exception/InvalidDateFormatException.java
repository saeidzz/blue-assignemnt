package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class InvalidDateFormatException extends AssignmentException {

    public InvalidDateFormatException() {
        super(InvalidDateFormatException.class.getSimpleName());
    }

    public InvalidDateFormatException(String message) {
        super(InvalidDateFormatException.class.getSimpleName(), message, ErrorType.USER_INPUT);
    }
}
