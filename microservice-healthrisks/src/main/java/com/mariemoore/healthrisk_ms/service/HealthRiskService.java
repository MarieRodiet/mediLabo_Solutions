package com.mariemoore.healthrisk_ms.service;

import com.mariemoore.healthrisk_ms.model.Note;
import com.mariemoore.healthrisk_ms.util.HealthRiskFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class HealthRiskService {

    private final RestTemplate restTemplate;

    @Autowired
    public HealthRiskService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Note> callNotesByPatientIdMicroservice(String patientId) {
        String notesMicroserviceUrl = "http://notes-ms:9000";
        String endpoint = "/api/notes/{patientId}"; // Specify the actual endpoint you want to call

        String url = notesMicroserviceUrl + endpoint.replace("{patientId}", patientId);

        ResponseEntity<List<Note>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Note>>() {});

        return responseEntity.getBody();
    }
    public String getRiskLevelOfPatient(String patientId, String gender, String age){
        List<Note> notes = this.callNotesByPatientIdMicroservice(patientId);
        return HealthRiskFinder.getHealthRiskLevel(notes, gender, age);

    }
}
