package com.blubank.doctorappointment.service.mapper;

import com.blubank.doctorappointment.domain.Doctor;
import com.blubank.doctorappointment.service.dto.DoctorDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface DoctorMapper {
    DoctorDto toDto(Doctor doctor);

    List<DoctorDto> toDto(List<Doctor> doctors);

    Doctor toEntity(DoctorDto doctor);
}
