package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class BadRequestAlertException extends AssignmentException {

    public BadRequestAlertException() {
        super(BadRequestAlertException.class.getSimpleName());
    }

    public BadRequestAlertException(String cause) {
        super(BadRequestAlertException.class.getSimpleName(), cause, ErrorType.USER_INPUT);
    }
}
