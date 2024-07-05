package com.mariemoore.healthrisk_ms.controller;

import com.mariemoore.healthrisk_ms.service.HealthRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing health risk assessments.
 * Provides endpoints for fetching the health risk level of patients based on various parameters.
 */
@Slf4j
@RestController
@RequestMapping("/api/healthrisks/")
public class HealthRiskController {

    @Autowired
    private HealthRiskService healthRiskService;

    /**
     * Retrieves the health risk level for a specified patient based on their ID, gender, and age.
     * This method uses the HealthRiskService to compute risk levels which can be used to guide clinical decisions.
     *
     * @param patientId The unique identifier of the patient.
     * @param gender The gender of the patient.
     * @param age The age of the patient.
     * @return ResponseEntity containing the health risk level as a string.
     */
    @GetMapping("{patientId}/{gender}/{age}")
    public ResponseEntity<String> getHealthRisk(@PathVariable String patientId, @PathVariable String gender, @PathVariable String age){
        log.info("GET /api/healthrisks/{}/{}/{} - Getting health risks of Patient with ID: {}", patientId, gender, age,  patientId);
        String healthRiskLevel = healthRiskService.getRiskLevelOfPatient(patientId, gender, age);
        return ResponseEntity.ok(healthRiskLevel);
    }
}
