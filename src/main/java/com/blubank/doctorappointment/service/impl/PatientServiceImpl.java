package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.repository.PatientRepository;
import com.blubank.doctorappointment.service.inf.PatientService;
import com.blubank.doctorappointment.service.dto.PatientDto;
import com.blubank.doctorappointment.service.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PatientDto save(PatientDto patient) {
        log.debug("Request to save Patient : {}", patient);
        return patientMapper.toDto(patientRepository.save(patientMapper.toEntity(patient)));
    }

    @Override
    public PatientDto update(PatientDto patient) {
        log.debug("Request to update Patient : {}", patient);
        return patientMapper.toDto(patientRepository.save(patientMapper.toEntity(patient)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDto> findAll() {
        log.debug("Request to get all Patients");
        return patientMapper.toDto(patientRepository.findAll());
    }

}
