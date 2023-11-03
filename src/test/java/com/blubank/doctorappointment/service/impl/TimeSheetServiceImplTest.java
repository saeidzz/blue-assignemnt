package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.api.vo.TimeSheetStatus;
import com.blubank.doctorappointment.domain.Patient;
import com.blubank.doctorappointment.domain.TimeSheet;
import com.blubank.doctorappointment.exception.TimeTakenBeforeException;
import com.blubank.doctorappointment.exception.TimeTakenException;
import com.blubank.doctorappointment.repository.PatientRepository;
import com.blubank.doctorappointment.repository.TimeSheetRepository;
import com.blubank.doctorappointment.service.dto.TimeSheetDto;
import com.blubank.doctorappointment.service.impl.factory.Factory;
import com.blubank.doctorappointment.service.mapper.TimeSheetMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TimeSheetServiceImplTest {
    @Mock
    TimeSheetRepository timeSheetRepository;
    @Spy
    TimeSheetMapper timeSheetMapper = Mappers.getMapper(TimeSheetMapper.class);
    @Mock
    PatientRepository patientRepository;
    @Mock
    EntityManager entityManager;
    @Mock
    Logger log;
    @InjectMocks
    TimeSheetServiceImpl timeSheetServiceImpl;


    @Test
    void testSave_successScenario() {
        TimeSheetDto timeSheetDto = Factory.getTimeSheetDto();
        when(timeSheetMapper.toDto(any(TimeSheet.class))).thenCallRealMethod();
        when(timeSheetMapper.toEntity(any(TimeSheetDto.class))).thenCallRealMethod();
        when(timeSheetRepository.save(any())).then(returnsFirstArg());
        TimeSheetDto result = timeSheetServiceImpl.save(timeSheetDto);
        Assertions.assertEquals(timeSheetDto, result);
    }

    @Test
    void testFindAll_OPEN_STATE_successScenario() {
        TimeSheet timeSheet = Factory.getTimeSheet();
        Page<TimeSheet> timeSheets = new PageImpl<>(List.of(timeSheet));
        when(timeSheetRepository.findAllByFromTimeAfterAndPatientIsNull(any(), any())).thenReturn(timeSheets);
        when(timeSheetMapper.toDto(any(List.class))).thenCallRealMethod();

        Page<TimeSheetDto> result = timeSheetServiceImpl.findAll(PageRequest.of(0, 10), TimeSheetStatus.OPEN);
        TimeSheetDto timeSheetDto = result.getContent().get(0);
        Assertions.assertEquals(timeSheet.getId(), timeSheetDto.getId());
        Assertions.assertEquals(timeSheet.getDate(), timeSheetDto.getDate());
        Assertions.assertEquals(timeSheet.getFromTime(), timeSheetDto.getFromTime());
        Assertions.assertEquals(timeSheet.getToTime(), timeSheetDto.getToTime());
    }

    @Test
    void testFindAll_TAKEN_STATUS_successScenario() {
        TimeSheet timeSheet = Factory.getTimeSheet();
        Page<TimeSheet> timeSheets = new PageImpl<>(List.of(timeSheet));
        when(timeSheetRepository.findAllByFromTimeAfterAndPatientIsNotNull(any(), any())).thenReturn(timeSheets);
        when(timeSheetMapper.toDto(any(List.class))).thenCallRealMethod();

        Page<TimeSheetDto> result = timeSheetServiceImpl.findAll(PageRequest.of(0, 10), TimeSheetStatus.TAKEN);
        TimeSheetDto timeSheetDto = result.getContent().get(0);
        Assertions.assertEquals(timeSheet.getId(), timeSheetDto.getId());
        Assertions.assertEquals(timeSheet.getDate(), timeSheetDto.getDate());
        Assertions.assertEquals(timeSheet.getFromTime(), timeSheetDto.getFromTime());
        Assertions.assertEquals(timeSheet.getToTime(), timeSheetDto.getToTime());
    }

    @Test
    void testFindAll_ALL_STATUS_successScenario() {
        TimeSheet timeSheet = Factory.getTimeSheet();
        Page<TimeSheet> timeSheets = new PageImpl<>(List.of(timeSheet));
        when(timeSheetRepository.findAll(any())).thenReturn(timeSheets);
        when(timeSheetMapper.toDto(any(List.class))).thenCallRealMethod();

        Page<TimeSheetDto> result = timeSheetServiceImpl.findAll(PageRequest.of(0, 10), TimeSheetStatus.ALL);
        TimeSheetDto timeSheetDto = result.getContent().get(0);
        Assertions.assertEquals(timeSheet.getId(), timeSheetDto.getId());
        Assertions.assertEquals(timeSheet.getDate(), timeSheetDto.getDate());
        Assertions.assertEquals(timeSheet.getFromTime(), timeSheetDto.getFromTime());
        Assertions.assertEquals(timeSheet.getToTime(), timeSheetDto.getToTime());
    }

    @Test
    void testFindAll_NULL_STATUS_failScenario() {
        TimeSheet timeSheet = Factory.getTimeSheet();
        Page<TimeSheet> timeSheets = new PageImpl<>(List.of(timeSheet));
        when(timeSheetRepository.findAll(any())).thenReturn(timeSheets);
        when(timeSheetMapper.toDto(any(List.class))).thenCallRealMethod();

        Assertions.assertThrows(NullPointerException.class, () -> {
            timeSheetServiceImpl.findAll(PageRequest.of(0, 10), null);
        });

    }

    @Test
    void testReserve_successScenario() {
        when(patientRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(Factory.getPatient()));
        TimeSheet timeSheet = Factory.getTimeSheet();
        timeSheet.setPatient(null);
        when(entityManager.find(any(), any(Long.class))).thenReturn(timeSheet);

        timeSheetServiceImpl.reserve(Factory.getReserveRequest());
        verify(timeSheetRepository, times(1)).save(any());
    }

    @Test
    void testReserve_failScenario_1() {
        when(patientRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(Factory.getPatient()));
        TimeSheet timeSheet = Factory.getTimeSheet();
        timeSheet.setPatient(Factory.getPatient());
        when(entityManager.find(any(), any(Long.class))).thenReturn(timeSheet);

        Assertions.assertThrows(TimeTakenException.class, () -> {
            timeSheetServiceImpl.reserve(Factory.getReserveRequest());
        });
    }

    @Test
    void testReserve_failScenario_2() {
        Patient patient = Factory.getPatient();
        when(patientRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(patient));
        TimeSheet timeSheet = Factory.getTimeSheet();
        timeSheet.setPatient(patient);
        when(entityManager.find(any(), any(Long.class))).thenReturn(timeSheet);

        Assertions.assertThrows(TimeTakenBeforeException.class, () -> {
            timeSheetServiceImpl.reserve(Factory.getReserveRequest());
        });
    }

    @Test
    void testGetByPatientPhoneNumber_successScenario() {
        TimeSheet timeSheet = Factory.getTimeSheet();
        when(timeSheetRepository.findAllByPatient_PhoneNumber(anyString())).thenReturn(List.of(timeSheet));
        when(timeSheetMapper.toDto(any(TimeSheet.class))).thenCallRealMethod();

        List<TimeSheetDto> result = timeSheetServiceImpl.getByPatientPhoneNumber("phoneNumber");
        TimeSheetDto timeSheetDto = result.get(0);
        Assertions.assertEquals(timeSheet.getId(), timeSheetDto.getId());
        Assertions.assertEquals(timeSheet.getDate(), timeSheetDto.getDate());
        Assertions.assertEquals(timeSheet.getFromTime(), timeSheetDto.getFromTime());
        Assertions.assertEquals(timeSheet.getToTime(), timeSheetDto.getToTime());
    }

    @Test
    void delete_successScenario() {
        TimeSheet timeSheet = Factory.getTimeSheet();
        timeSheet.setPatient(null);
        when(entityManager.find(any(), any(Long.class))).thenReturn(timeSheet);
        timeSheetServiceImpl.delete(timeSheet.getId());
        verify(timeSheetRepository, times(1)).delete(any());
    }

    @Test
    void delete_failScenario() {
        TimeSheet timeSheet = Factory.getTimeSheet();

        when(entityManager.find(any(), any(Long.class))).thenReturn(timeSheet);
        Assertions.assertThrows(TimeTakenException.class, () -> {
            timeSheetServiceImpl.delete(timeSheet.getId());
        });
    }

}

