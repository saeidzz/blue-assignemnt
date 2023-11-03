package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;

public class PhoneNumberNotFoundException extends AssignmentException {

    public PhoneNumberNotFoundException() {
        super(PhoneNumberNotFoundException.class.getSimpleName());
    }

    public PhoneNumberNotFoundException(String message) {
        super(PhoneNumberNotFoundException.class.getSimpleName(), message, ErrorType.USER_INPUT);
    }
}
