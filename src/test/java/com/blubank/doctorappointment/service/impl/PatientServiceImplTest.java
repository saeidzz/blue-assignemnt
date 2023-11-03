package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.domain.Patient;
import com.blubank.doctorappointment.repository.PatientRepository;
import com.blubank.doctorappointment.service.dto.PatientDto;
import com.blubank.doctorappointment.service.impl.factory.Factory;
import com.blubank.doctorappointment.service.mapper.PatientMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PatientServiceImplTest {
    @Mock
    PatientRepository patientRepository;
    @Spy
    PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);
    @Mock
    Logger log;
    @InjectMocks
    PatientServiceImpl patientServiceImpl;

    @Test
    void testSave_successScenario() {
        PatientDto patient = Factory.getPatientDto();
        when(patientMapper.toDto(any(Patient.class))).thenCallRealMethod();
        when(patientMapper.toEntity(any(PatientDto.class))).thenCallRealMethod();
        when(patientRepository.save(any())).then(returnsFirstArg());
        PatientDto result = patientServiceImpl.save(patient);
        Assertions.assertEquals(patient, result);
    }

    @Test
    void testUpdate_successScenario() {
        when(patientMapper.toDto(any(Patient.class))).thenCallRealMethod();
        when(patientMapper.toEntity(any(PatientDto.class))).thenCallRealMethod();
        PatientDto patientDto = Factory.getPatientDto();
        when(patientRepository.save(any())).then(returnsFirstArg());
        PatientDto result = patientServiceImpl.update(patientDto);
        Assertions.assertEquals(patientDto, result);
    }

    @Test
    void testFindAll_successScenario() {
        Patient patient1 = Factory.getPatient();
        Patient patient2 = Factory.getPatient();
        when(patientMapper.toDto(any(List.class))).thenCallRealMethod();
        List<Patient> patients = List.of(patient1, patient2);
        when(patientRepository.findAll()).thenReturn(patients);
        Map<Long, List<PatientDto>> groupedResult = patientServiceImpl.findAll().stream().collect(Collectors.groupingBy(PatientDto::getId));
        PatientDto patientDto1 = groupedResult.get(patient1.getId()).get(0);
        PatientDto patientDto2 = groupedResult.get(patient2.getId()).get(0);

        Assertions.assertEquals(patient1.getId(), patientDto1.getId());
        Assertions.assertEquals(patient1.getFullName(), patientDto1.getFullName());
        Assertions.assertEquals(patient1.getPhoneNumber(), patientDto1.getPhoneNumber());

        Assertions.assertEquals(patient2.getId(), patientDto2.getId());
        Assertions.assertEquals(patient2.getFullName(), patientDto2.getFullName());
        Assertions.assertEquals(patient2.getPhoneNumber(), patientDto2.getPhoneNumber());
    }
}

