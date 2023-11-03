package com.blubank.doctorappointment.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;

@Value
@EqualsAndHashCode(exclude = "id")
public class DoctorDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    Long id;
    String fullName;

}
