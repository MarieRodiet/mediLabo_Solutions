package com.mariemoore.healthrisk_ms.controller;

import com.mariemoore.healthrisk_ms.service.HealthRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/healthrisks/")
public class HealthRiskController {

    @Autowired
    private HealthRiskService healthRiskService;
    @GetMapping("{patientId}/{gender}/{age}")
    public ResponseEntity<String> getHealthRisk(@PathVariable String patientId, @PathVariable String gender, @PathVariable String age){
        log.info("GET /api/healthrisks/{}/{}/{} - Getting health risks of Patient with ID: {}", patientId, gender, age,  patientId);
        String healthRiskLevel = healthRiskService.getRiskLevelOfPatient(patientId, gender, age);
        return ResponseEntity.ok(healthRiskLevel);
    }
}
