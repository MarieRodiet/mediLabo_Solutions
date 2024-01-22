package com.mariemoore.patientsm.service;

import com.mariemoore.patientsm.model.Patient;
import com.mariemoore.patientsm.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(String id) {
        return patientRepository.findById(id);
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient updatedPatient) {
        if (patientRepository.existsById(updatedPatient.getId())) {
            updatedPatient.setId(updatedPatient.getId());
            return patientRepository.save(updatedPatient);
        } else {
            throw new IllegalArgumentException("Patient not found.");
        }
    }

    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }
}

