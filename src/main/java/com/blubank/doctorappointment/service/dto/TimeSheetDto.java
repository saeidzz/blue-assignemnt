package com.blubank.doctorappointment.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(exclude = "id")
public class TimeSheetDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    Long id;
    LocalDate date;
    LocalDateTime fromTime;
    LocalDateTime toTime;
    DoctorDto doctor;
    PatientDto patient;

}
