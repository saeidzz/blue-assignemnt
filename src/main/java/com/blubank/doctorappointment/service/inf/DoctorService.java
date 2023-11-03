package com.blubank.doctorappointment.service.inf;

import com.blubank.doctorappointment.api.vo.TimePickingVO;
import com.blubank.doctorappointment.service.dto.DoctorDto;

public interface DoctorService {

    DoctorDto save(DoctorDto doctor);

    DoctorDto update(DoctorDto doctor);

    void pickTime(TimePickingVO timePicking);
}
