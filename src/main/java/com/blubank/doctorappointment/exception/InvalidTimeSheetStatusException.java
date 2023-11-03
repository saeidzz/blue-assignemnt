package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class InvalidTimeSheetStatusException extends AssignmentException {

    public InvalidTimeSheetStatusException() {
        super(InvalidTimeSheetStatusException.class.getSimpleName());
    }

    public InvalidTimeSheetStatusException(String message) {
        super(InvalidTimeSheetStatusException.class.getSimpleName(), message, ErrorType.USER_INPUT);
    }
}
