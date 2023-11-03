package com.blubank.doctorappointment.api;

import com.blubank.doctorappointment.api.vo.TimePickingVO;
import com.blubank.doctorappointment.exception.BadRequestAlertException;
import com.blubank.doctorappointment.service.inf.DoctorService;
import com.blubank.doctorappointment.service.dto.DoctorDto;
import com.blubank.doctorappointment.util.HeaderUtil;
import com.blubank.doctorappointment.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DoctorResource {

    private static final String ENTITY_NAME = "doctor";
    private final Logger log = LoggerFactory.getLogger(DoctorResource.class);
    private final DoctorService doctorService;
    @Value("${clientApp.name}")
    private String applicationName;

    @PostMapping("/doctors")
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctor) throws URISyntaxException {
        log.debug("REST request to save Doctor : {}", doctor);
        if (doctor.getId() != null) {
            throw new BadRequestAlertException("A new doctor cannot already have an ID");
        }
        doctorService.save(doctor);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/doctors")
    public ResponseEntity<DoctorDto> updateDoctor(
            @Valid @RequestBody DoctorDto doctor
    ) {
        log.debug("REST request to update Doctor : {}", doctor);
        if (doctor.getId() == null) {
            throw new BadRequestAlertException("Invalid id");
        }
        DoctorDto result = doctorService.update(doctor);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doctor.getId().toString()))
                .body(result);
    }

    @PostMapping("/time-pick")
    public ResponseEntity<Void> timePicking(@Valid @RequestBody TimePickingVO timePicking) throws URISyntaxException {
        log.debug("REST request for Doctor timePicking  : {}", timePicking);
        ValidationUtil.timePickingValidate(timePicking);
        doctorService.pickTime(timePicking);
        return ResponseEntity.created(new URI("/api/time-sheets/OPEN")).build();
    }


}
