package com.blubank.doctorappointment.service.mapper;

import com.blubank.doctorappointment.domain.Patient;
import com.blubank.doctorappointment.service.dto.PatientDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface PatientMapper {
    PatientDto toDto(Patient patient);

    List<PatientDto> toDto(List<Patient> patients);

    Patient toEntity(PatientDto timeSheet);
}
