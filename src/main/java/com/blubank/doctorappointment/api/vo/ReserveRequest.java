package com.blubank.doctorappointment.api.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record ReserveRequest(
        @NotNull(message = "timesheetId must not be null") @NotEmpty(message = "timesheetId must not be empty") Long timeSheetId,
        @NotNull(message = "phoneNumber must not be null") @NotEmpty(message = "phoneNumber must not be empty") String phoneNumber) {
}
