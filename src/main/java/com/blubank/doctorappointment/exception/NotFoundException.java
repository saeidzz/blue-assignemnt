package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class NotFoundException extends AssignmentException {

    public NotFoundException() {
        super(NotFoundException.class.getSimpleName());
    }

    public NotFoundException(String cause) {
        super(NotFoundException.class.getSimpleName(), cause, ErrorType.USER_INPUT);
    }
}
