package com.blubank.doctorappointment.api;

import com.blubank.doctorappointment.api.vo.ReserveRequest;
import com.blubank.doctorappointment.api.vo.TimeSheetStatus;
import com.blubank.doctorappointment.service.dto.TimeSheetDto;
import com.blubank.doctorappointment.service.inf.TimeSheetService;
import com.blubank.doctorappointment.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TimeSheetResource {

    private final TimeSheetService timeSheetService;

    @GetMapping("/time-sheets/{status}")
    public ResponseEntity<List<TimeSheetDto>> getAllTimeSheets(@ParameterObject Pageable pageable, @PathVariable("status") TimeSheetStatus status) {
        log.debug("REST request to get a page of TimeSheets");
        Page<TimeSheetDto> page = timeSheetService.findAll(pageable, status);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/time-sheets/reserve")
    public ResponseEntity<Void> reserve(@Valid @RequestBody ReserveRequest reserveRequest) {
        log.debug("REST request to get a page of TimeSheets");
        timeSheetService.reserve(reserveRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/time-sheets/reserved")
    public ResponseEntity<List<TimeSheetDto>> getPatientTimeSheets(@RequestParam String phoneNumber) {
        log.debug("REST request to get a page of TimeSheets by phone number + {}", phoneNumber);
        List<TimeSheetDto> result = timeSheetService.getByPatientPhoneNumber(phoneNumber);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/time-sheets/{id}")
    public ResponseEntity<List<TimeSheetDto>> getPatientTimeSheets(@PathVariable("id") Long id) {
        log.debug("REST request to delete timesheet by id {}", id);
        timeSheetService.delete(id);
        return ResponseEntity.ok().build();
    }

}
