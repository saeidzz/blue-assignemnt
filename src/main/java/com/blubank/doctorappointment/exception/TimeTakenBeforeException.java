package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class TimeTakenBeforeException extends AssignmentException {

    public TimeTakenBeforeException() {
        super(TimeTakenBeforeException.class.getSimpleName());
    }

    public TimeTakenBeforeException(String message) {
        super(TimeTakenBeforeException.class.getSimpleName(), message, ErrorType.USER_INPUT);
    }
}
