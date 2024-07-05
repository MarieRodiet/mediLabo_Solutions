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

/**
 * REST controller for managing {@link Patient} resources.
 * Provides endpoints for CRUD operations on patients within the system.
 */
@Slf4j
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;


    /**
     * Retrieves a list of all patients in the system.
     *
     * @return ResponseEntity containing the list of patients.
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        log.info("GET /api/patients - Getting all patients");
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    /**
     * Retrieves a single patient by their ID.
     *
     * @param id The ID of the patient to retrieve.
     * @return ResponseEntity containing the patient if found, or HTTP 404 Not Found if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        log.info("GET /api/patients/{} - Getting patient by ID: {}", id, id);
        Optional<Patient> patient = patientService.getPatientById(Integer.parseInt(id));
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new patient in the system.
     *
     * @param patient The patient data to create.
     * @return ResponseEntity containing the created patient, or HTTP 500 Internal Server Error on failure.
     */
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

    /**
     * Updates an existing patient's data.
     *
     * @param updatedPatient The updated patient data.
     * @return ResponseEntity containing the updated patient, or HTTP 500 Internal Server Error on failure.
     */
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

    /**
     * Deletes a patient from the system by their ID.
     *
     * @param id The ID of the patient to delete.
     * @return ResponseEntity containing the deleted patient if successful, or HTTP 404 Not Found if the patient does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Patient>> deletePatient(@PathVariable String id) {
        log.info("DELETE /api/patients/{} - Deleting patient by ID: {}", id, id);
        int intId = Integer.parseInt(id);
        Optional<Patient> patient = patientService.getPatientById(intId);
        if (patient.isPresent()) {
            patientService.deletePatient(intId);
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

