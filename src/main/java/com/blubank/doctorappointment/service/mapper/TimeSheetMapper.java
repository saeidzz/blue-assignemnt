package com.blubank.doctorappointment.service.mapper;

import com.blubank.doctorappointment.domain.TimeSheet;
import com.blubank.doctorappointment.service.dto.TimeSheetDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface TimeSheetMapper {
    TimeSheetDto toDto(TimeSheet timeSheet);

    List<TimeSheetDto> toDto(List<TimeSheet> timeSheets);

    TimeSheet toEntity(TimeSheetDto timeSheet);
}
