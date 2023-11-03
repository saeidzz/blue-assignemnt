package com.blubank.doctorappointment.exception;


import com.blubank.doctorappointment.enums.ErrorType;

public class AssignmentException extends RuntimeException {

    private final String message;
    private String cause;
    private ErrorType type = ErrorType.BUSINESS;


    public AssignmentException(String message) {
        super(message);
        this.message = message;
    }

    public AssignmentException(String message, String cause, ErrorType type) {
        super(message);
        this.message = message;
        this.cause = cause;
        this.type = type;
    }

    public AssignmentException(String message, String message1, String cause) {
        super(message);
        this.message = message1;
        this.cause = cause;
    }

    public AssignmentException(String message, Throwable cause, String message1, String cause1) {
        super(message, cause);
        this.message = message1;
        this.cause = cause1;
    }

    public AssignmentException(Throwable cause, String message, String cause1) {
        super(message);
        this.message = message;
        this.cause = cause1;
    }

    public AssignmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                               String message1, String cause1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message = message1;
        this.cause = cause1;
    }

    public AssignmentException(String message, ErrorType errorType) {
        super(message);
        this.message = getMessage();
        this.type = errorType;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
