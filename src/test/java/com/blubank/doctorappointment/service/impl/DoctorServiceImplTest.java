package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.api.vo.TimePickingVO;
import com.blubank.doctorappointment.exception.BadRequestAlertException;
import com.blubank.doctorappointment.repository.DoctorRepository;
import com.blubank.doctorappointment.service.inf.TimeSheetService;
import com.blubank.doctorappointment.service.dto.DoctorDto;
import com.blubank.doctorappointment.service.dto.TimeSheetDto;
import com.blubank.doctorappointment.service.impl.factory.Factory;
import com.blubank.doctorappointment.service.mapper.DoctorMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DoctorServiceImplTest {
    @Mock
    DoctorRepository doctorRepository;
    @Spy
    DoctorMapper doctorMapper = Mappers.getMapper(DoctorMapper.class);
    @Mock
    TimeSheetService timeSheetService;
    @Mock
    Logger log;
    @InjectMocks
    DoctorServiceImpl doctorServiceImpl;

    @Test
    void testSave_successScenario() {
        when(doctorMapper.toDto(any(List.class))).thenCallRealMethod();
        when(doctorMapper.toEntity(any())).thenCallRealMethod();
        when(doctorRepository.save(any())).then(returnsFirstArg());
        when(doctorRepository.findAll()).thenReturn(new ArrayList<>());
        DoctorDto doctorDto = Factory.getDoctorDto();
        DoctorDto result = doctorServiceImpl.save(doctorDto);
        Assertions.assertEquals(doctorDto, result);
    }

    @Test
    void testSave_failScenario() {
        when(doctorMapper.toDto(any(List.class))).thenCallRealMethod();
        when(doctorMapper.toEntity(any())).thenCallRealMethod();
        when(doctorRepository.save(any())).then(returnsFirstArg());
        when(doctorRepository.findAll()).thenReturn(List.of(Factory.getDoctor()));
        DoctorDto doctorDto = Factory.getDoctorDto();
        Assertions.assertThrows(BadRequestAlertException.class, () -> doctorServiceImpl.save(doctorDto));

    }

    @Test
    void testUpdate_successScenario() {
        when(doctorMapper.toDto(any(List.class))).thenCallRealMethod();
        when(doctorMapper.toEntity(any())).thenCallRealMethod();
        when(doctorRepository.existsById(any())).thenReturn(true);
        DoctorDto doctorDto = Factory.getDoctorDto();
        when(doctorRepository.save(any())).then(returnsFirstArg());

        DoctorDto result = doctorServiceImpl.update(doctorDto);
        Assertions.assertEquals(doctorDto, result);
    }

    @Test
    void testUpdate_failScenario() {
        when(doctorMapper.toDto(any(List.class))).thenCallRealMethod();
        when(doctorMapper.toEntity(any())).thenCallRealMethod();
        when(doctorRepository.existsById(any())).thenReturn(false);
        DoctorDto doctorDto = Factory.getDoctorDto();
        when(doctorRepository.save(any())).then(returnsFirstArg());

        Assertions.assertThrows(BadRequestAlertException.class, () -> doctorServiceImpl.update(doctorDto));
    }

    @Test
    void testPickTime_successScenario_48_times() {
        TimeSheetDto timeSheetDto = Factory.getTimeSheetDto();
        when(timeSheetService.save(any())).thenReturn(timeSheetDto);
        when(doctorRepository.findAll()).thenReturn(List.of(Factory.getDoctor()));
        TimePickingVO timePicking = Factory.getTimePicking();
        doctorServiceImpl.pickTime(timePicking);
        verify(timeSheetService, times(48)).save(any());
    }

    @Test
    void testPickTime_successScenario_21_times() {
        TimeSheetDto timeSheetDto = Factory.getTimeSheetDto();
        when(timeSheetService.save(any())).thenReturn(timeSheetDto);
        when(doctorRepository.findAll()).thenReturn(List.of(Factory.getDoctor()));
        LocalDateTime now = LocalDateTime.now();
        TimePickingVO timePicking = new TimePickingVO(now, now.plus(650, ChronoUnit.MINUTES));
        doctorServiceImpl.pickTime(timePicking);
        verify(timeSheetService, times(21)).save(any());
    }

    @Test
    void testPickTime_successScenario_22_times() {
        TimeSheetDto timeSheetDto = Factory.getTimeSheetDto();
        when(timeSheetService.save(any())).thenReturn(timeSheetDto);
        when(doctorRepository.findAll()).thenReturn(List.of(Factory.getDoctor()));
        LocalDateTime now = LocalDateTime.now();
        TimePickingVO timePicking = new TimePickingVO(now, now.plus(660, ChronoUnit.MINUTES));
        doctorServiceImpl.pickTime(timePicking);
        verify(timeSheetService, times(22)).save(any());
    }

    @Test
    void testPickTime_successScenario_0_times() {
        TimeSheetDto timeSheetDto = Factory.getTimeSheetDto();
        when(timeSheetService.save(any())).thenReturn(timeSheetDto);
        when(doctorRepository.findAll()).thenReturn(List.of(Factory.getDoctor()));
        LocalDateTime now = LocalDateTime.now();
        TimePickingVO timePicking = new TimePickingVO(now, now.plus(20, ChronoUnit.MINUTES));
        doctorServiceImpl.pickTime(timePicking);
        verify(timeSheetService, times(0)).save(any());
    }
}
