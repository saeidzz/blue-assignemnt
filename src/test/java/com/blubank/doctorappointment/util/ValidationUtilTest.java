package com.blubank.doctorappointment.util;

import com.blubank.doctorappointment.api.vo.TimePickingVO;
import com.blubank.doctorappointment.exception.InvalidInputDateException;
import com.blubank.doctorappointment.exception.StartAndEndDateMustBeSameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


class ValidationUtilTest {

    @Test
    void testTimePickingValidate_successScenario() {
        ValidationUtil.timePickingValidate(new TimePickingVO(LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MIN)));
    }

    @Test
    void testTimePickingValidate_failScenario_notSameDate() {
        Assertions.assertThrows(StartAndEndDateMustBeSameException.class, () -> {
            ValidationUtil.timePickingValidate(new TimePickingVO(LocalDateTime.now(), LocalDateTime.now().plus(2, ChronoUnit.DAYS)));
        });
    }

    @Test
    void testTimePickingValidate_failScenario_endDateBeforeStartDate() {
        Assertions.assertThrows(InvalidInputDateException.class, () -> {
            ValidationUtil.timePickingValidate(new TimePickingVO(LocalDateTime.now(), LocalDateTime.now().plus(-1, ChronoUnit.MINUTES)));
        });
    }
}

