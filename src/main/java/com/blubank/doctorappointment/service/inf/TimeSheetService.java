package com.blubank.doctorappointment.service.inf;

import com.blubank.doctorappointment.api.vo.ReserveRequest;
import com.blubank.doctorappointment.api.vo.TimeSheetStatus;
import com.blubank.doctorappointment.service.dto.TimeSheetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TimeSheetService {

    TimeSheetDto save(TimeSheetDto timeSheet);

    Page<TimeSheetDto> findAll(Pageable pageable, TimeSheetStatus status);

    void reserve(ReserveRequest reserveRequest);

    List<TimeSheetDto> getByPatientPhoneNumber(String phoneNumber);

    void delete(Long id);
}
