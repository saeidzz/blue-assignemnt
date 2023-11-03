package com.blubank.doctorappointment.api.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record TimePickingVO(
        @NotNull(message = "startTime must not be null") @NotEmpty(message = "startTime must not be empty") LocalDateTime startTime,
        @NotNull(message = "endTime must not be null") @NotEmpty(message = "endTime must not be empty") LocalDateTime endTime) {
}
