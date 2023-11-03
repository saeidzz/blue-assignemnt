package com.blubank.doctorappointment.service.impl.factory;

import com.blubank.doctorappointment.api.vo.ReserveRequest;
import com.blubank.doctorappointment.api.vo.TimePickingVO;
import com.blubank.doctorappointment.domain.Doctor;
import com.blubank.doctorappointment.domain.Patient;
import com.blubank.doctorappointment.domain.TimeSheet;
import com.blubank.doctorappointment.service.dto.DoctorDto;
import com.blubank.doctorappointment.service.dto.PatientDto;
import com.blubank.doctorappointment.service.dto.TimeSheetDto;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Factory {
    public static final Faker faker = new Faker();

    public static DoctorDto getDoctorDto() {
        return new DoctorDto(faker.random().nextLong(), faker.name().fullName());
    }

    public static PatientDto getPatientDto() {
        return new PatientDto(faker.random().nextLong(), faker.name().fullName(), faker.phoneNumber().phoneNumber());
    }

    public static TimeSheetDto getTimeSheetDto() {
        LocalDateTime localDateTime = LocalDateTime.now().plus(Math.abs(faker.random().nextInt(1)), ChronoUnit.DAYS);
        return new TimeSheetDto(faker.random().nextLong(), localDateTime.toLocalDate(), localDateTime, localDateTime.plusMinutes(30), getDoctorDto(), getPatientDto());
    }

    public static Doctor getDoctor() {
        return new Doctor(faker.random().nextLong(), faker.name().fullName());
    }

    public static Patient getPatient() {
        return new Patient(faker.random().nextLong(), faker.name().fullName(), faker.phoneNumber().phoneNumber());
    }

    public static TimeSheet getTimeSheet() {
        LocalDateTime localDateTime = LocalDateTime.now().plus(Math.abs(faker.random().nextInt(1)), ChronoUnit.DAYS);
        return new TimeSheet(faker.random().nextLong(), localDateTime.toLocalDate(), localDateTime, localDateTime.plusMinutes(30), getDoctor(), getPatient());
    }

    public static TimePickingVO getTimePicking() {
        return new TimePickingVO(LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.DAYS));
    }


    public static ReserveRequest getReserveRequest() {
        return new ReserveRequest(faker.random().nextLong(), faker.phoneNumber().phoneNumber());
    }
}
