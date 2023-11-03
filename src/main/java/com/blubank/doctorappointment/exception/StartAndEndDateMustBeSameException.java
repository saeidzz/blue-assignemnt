package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class StartAndEndDateMustBeSameException extends AssignmentException {

    public StartAndEndDateMustBeSameException() {
        super(StartAndEndDateMustBeSameException.class.getSimpleName());
    }

    public StartAndEndDateMustBeSameException(String message) {
        super(StartAndEndDateMustBeSameException.class.getSimpleName(), message, ErrorType.USER_INPUT);
    }
}
