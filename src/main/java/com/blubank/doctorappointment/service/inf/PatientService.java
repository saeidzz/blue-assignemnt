package com.blubank.doctorappointment.service.inf;

import com.blubank.doctorappointment.service.dto.PatientDto;

import java.util.List;


public interface PatientService {

    PatientDto save(PatientDto patient);

    PatientDto update(PatientDto patient);

    List<PatientDto> findAll();

}
