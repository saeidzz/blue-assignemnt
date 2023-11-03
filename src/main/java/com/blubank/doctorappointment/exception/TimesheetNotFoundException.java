package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class TimesheetNotFoundException extends AssignmentException {

    public TimesheetNotFoundException() {
        super(TimesheetNotFoundException.class.getSimpleName());
    }

    public TimesheetNotFoundException(String message) {
        super(TimesheetNotFoundException.class.getSimpleName(), message, ErrorType.USER_INPUT);
    }
}
