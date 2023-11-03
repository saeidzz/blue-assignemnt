package com.blubank.doctorappointment.api;

import com.blubank.doctorappointment.exception.BadRequestAlertException;
import com.blubank.doctorappointment.repository.PatientRepository;
import com.blubank.doctorappointment.service.inf.PatientService;
import com.blubank.doctorappointment.service.dto.PatientDto;
import com.blubank.doctorappointment.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PatientResource {

    private static final String ENTITY_NAME = "patient";
    private final Logger log = LoggerFactory.getLogger(PatientResource.class);
    private final PatientService patientService;
    private final PatientRepository patientRepository;
    @Value("${clientApp.name}")
    private String applicationName;

    @PostMapping("/patients")
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patient) throws URISyntaxException {
        log.debug("REST request to save Patient : {}", patient);
        if (patient.getId() != null) {
            throw new BadRequestAlertException("A new patient cannot already have an ID");
        }
        PatientDto result = patientService.save(patient);
        return ResponseEntity.created(new URI("/api/patients/" + result.getId())).headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString())).body(result);
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable(value = "id", required = false) final Long id, @RequestBody PatientDto patient) throws URISyntaxException {
        log.debug("REST request to update Patient : {}, {}", id, patient);
        if (patient.getId() == null) {
            throw new BadRequestAlertException("Invalid id");
        }
        if (!Objects.equals(id, patient.getId())) {
            throw new BadRequestAlertException("Invalid ID");
        }

        if (!patientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found");
        }

        PatientDto result = patientService.update(patient);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patient.getId().toString())).body(result);
    }


    @GetMapping("/patients")
    public List<PatientDto> getAllPatients() {
        log.debug("REST request to get all Patients");
        return patientService.findAll();
    }
}
