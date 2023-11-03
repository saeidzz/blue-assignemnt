package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class ErrorResponse {
    String message;
    String code;
    ErrorType type;
}
