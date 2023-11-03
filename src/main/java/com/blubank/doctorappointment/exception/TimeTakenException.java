package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class TimeTakenException extends AssignmentException {

    public TimeTakenException() {
        super(TimeTakenException.class.getSimpleName());
    }

    public TimeTakenException(String message) {
        super(TimeTakenException.class.getSimpleName(), message, ErrorType.USER_INPUT);
    }
}
