package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.api.vo.ReserveRequest;
import com.blubank.doctorappointment.api.vo.TimeSheetStatus;
import com.blubank.doctorappointment.domain.Patient;
import com.blubank.doctorappointment.domain.TimeSheet;
import com.blubank.doctorappointment.exception.*;
import com.blubank.doctorappointment.repository.PatientRepository;
import com.blubank.doctorappointment.repository.TimeSheetRepository;
import com.blubank.doctorappointment.service.dto.TimeSheetDto;
import com.blubank.doctorappointment.service.inf.TimeSheetService;
import com.blubank.doctorappointment.service.mapper.TimeSheetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Service Implementation for managing {@link TimeSheet}.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TimeSheetServiceImpl implements TimeSheetService {
    private final TimeSheetRepository timeSheetRepository;
    private final TimeSheetMapper timeSheetMapper;
    private final PatientRepository patientRepository;
    private final EntityManager entityManager;

    @Override
    public TimeSheetDto save(TimeSheetDto timeSheet) {
        log.debug("Request to save TimeSheet : {}", timeSheet);
        return timeSheetMapper.toDto(timeSheetRepository.save(timeSheetMapper.toEntity(timeSheet)));
    }


    @Override
    @Transactional(readOnly = true)
    public Page<TimeSheetDto> findAll(Pageable pageable, TimeSheetStatus status) {
        log.debug("Request to get all TimeSheets");
        Page<TimeSheet> all = null;

        switch (status) {
            case OPEN ->
                    all = timeSheetRepository.findAllByFromTimeAfterAndPatientIsNull(LocalDate.now().atStartOfDay(), pageable);
            case TAKEN ->
                    all = timeSheetRepository.findAllByFromTimeAfterAndPatientIsNotNull(LocalDate.now().atStartOfDay(), pageable);

            case ALL -> all = timeSheetRepository.findAll(pageable);

            default -> throw new InvalidTimeSheetStatusException();
        }
        return new PageImpl<>(timeSheetMapper.toDto(all.getContent()), all.getPageable(), all.getTotalElements());
    }

    @Override
    @Transactional
    public void reserve(ReserveRequest reserveRequest) {
        TimeSheet timeSheet = getSyncronizedTimeSheet(reserveRequest.timeSheetId());
        Patient patient = patientRepository.findByPhoneNumber(reserveRequest.phoneNumber()).orElseThrow(PhoneNumberNotFoundException::new);

        if (!Objects.isNull(timeSheet.getPatient())) {
            if (timeSheet.getPatient().equals(patient)) {
                throw new TimeTakenBeforeException("time reserved before ! ");
            }
            throw new TimeTakenException("please pick another time ! time reserved by another patient");

        }
        timeSheet.setPatient(patient);
        timeSheetRepository.save(timeSheet);
    }

    private TimeSheet getSyncronizedTimeSheet(Long id) {
        TimeSheet timeSheet = entityManager.find(TimeSheet.class, id);
        if (timeSheet == null) {
            throw new TimesheetNotFoundException();
        }
        entityManager.lock(timeSheet, LockModeType.PESSIMISTIC_WRITE);
        return timeSheet;
    }

    @Override
    public List<TimeSheetDto> getByPatientPhoneNumber(String phoneNumber) {
        return timeSheetMapper.toDto(timeSheetRepository.findAllByPatient_PhoneNumber(phoneNumber));
    }

    public void delete(Long id) {
        TimeSheet timeSheet = getSyncronizedTimeSheet(id);
        if (!Objects.isNull(timeSheet.getPatient())){
            throw new TimeTakenException();
        }
        timeSheetRepository.delete(timeSheet);
    }
}
