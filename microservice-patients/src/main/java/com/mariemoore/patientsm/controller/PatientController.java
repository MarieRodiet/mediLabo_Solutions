package com.mariemoore.patientsm.controller;

import com.mariemoore.patientsm.model.Patient;
import com.mariemoore.patientsm.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;


    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        log.info("GET /api/patients - Getting all patients");
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        log.info("GET /api/patients/{} - Getting patient by ID: {}", id, id);
        Optional<Patient> patient = patientService.getPatientById(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        log.info("POST /api/patients - Creating a new patient: {}", patient);
        try {
            Patient createdPatient = patientService.createPatient(patient);
            return ResponseEntity.ok(createdPatient);
        } catch (Exception e) {
            log.error("Error creating patient: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient updatedPatient) {
        log.info("PUT /api/patients - Updating patient: {}", updatedPatient);
        try {
            Patient updated = patientService.updatePatient(updatedPatient);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            log.error("Error updating patient: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Patient>> deletePatient(@PathVariable String id) {
        log.info("DELETE /api/patients/{} - Deleting patient by ID: {}", id, id);
        Optional<Patient> patient = patientService.getPatientById(id);
        if(patient != null){
            patientService.deletePatient(id);
        }
        return ResponseEntity.ok(patient);
    }
}

