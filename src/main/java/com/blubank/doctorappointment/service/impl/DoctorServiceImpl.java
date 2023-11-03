package com.blubank.doctorappointment.service.impl;

import com.blubank.doctorappointment.api.vo.TimePickingVO;
import com.blubank.doctorappointment.domain.Doctor;
import com.blubank.doctorappointment.exception.BadRequestAlertException;
import com.blubank.doctorappointment.repository.DoctorRepository;
import com.blubank.doctorappointment.util.CalendarUtil;
import com.blubank.doctorappointment.service.inf.DoctorService;
import com.blubank.doctorappointment.service.inf.TimeSheetService;
import com.blubank.doctorappointment.service.dto.DoctorDto;
import com.blubank.doctorappointment.service.dto.TimeSheetDto;
import com.blubank.doctorappointment.service.mapper.DoctorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.IntStream;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final TimeSheetService timeSheetService;

    @Override
    public DoctorDto save(DoctorDto doctor) {
        log.debug("Request to save Doctor : {}", doctor);
        if (doctorRepository.findAll().size() > 0) {
            throw new BadRequestAlertException("doctor added before !");
        }
        return doctorMapper.toDto(doctorRepository.save(doctorMapper.toEntity(doctor)));
    }

    @Override
    public DoctorDto update(DoctorDto doctor) {
        log.debug("Request to update Doctor : {}", doctor);
        if (!doctorRepository.existsById(doctor.getId())) {
            throw new BadRequestAlertException("Entity not found");
        }
        return doctorMapper.toDto(doctorRepository.save(doctorMapper.toEntity(doctor)));
    }


    @Override
    public void pickTime(TimePickingVO timePicking) {
        int slicedIn30Minutes = CalendarUtil.diffMinutes(timePicking.startTime(), timePicking.endTime()) / 30;
        Doctor doctor = doctorRepository.findAll().get(0);
        IntStream.range(0, slicedIn30Minutes).forEach(startTime -> {
            LocalDateTime lastStartTime = timePicking.startTime().plusMinutes(startTime * 30L);
            timeSheetService.save(new TimeSheetDto(null, lastStartTime.toLocalDate(), lastStartTime, lastStartTime.plusMinutes(30), doctorMapper.toDto(doctor), null));
        });
    }
}
