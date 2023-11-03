package com.blubank.doctorappointment.util;

import com.blubank.doctorappointment.api.vo.TimePickingVO;
import com.blubank.doctorappointment.exception.InvalidInputDateException;
import com.blubank.doctorappointment.exception.StartAndEndDateMustBeSameException;

public final class ValidationUtil {
    public static void timePickingValidate(TimePickingVO timePickingVO) {

        if (!timePickingVO.startTime().toLocalDate().equals(timePickingVO.endTime().toLocalDate())) {
            throw new StartAndEndDateMustBeSameException("timePicking must be done for one day !");
        }


        if (timePickingVO.startTime().isAfter(timePickingVO.endTime())) {
            throw new InvalidInputDateException("endTime must not be before startTime |");
        }
    }
}
