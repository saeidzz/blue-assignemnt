package com.blubank.doctorappointment.repository;

import com.blubank.doctorappointment.domain.TimeSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSheetRepository extends CrudRepository<TimeSheet, Long> {

    Page<TimeSheet> findAll(Pageable pageable);

    Page<TimeSheet> findAllByFromTimeAfterAndPatientIsNull(LocalDateTime today, Pageable pageable);

    Page<TimeSheet> findAllByFromTimeAfterAndPatientIsNotNull(LocalDateTime today, Pageable pageable);

    List<TimeSheet> findAllByPatient_PhoneNumber(String phoneNumber);



}
