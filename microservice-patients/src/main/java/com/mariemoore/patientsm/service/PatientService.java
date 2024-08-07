package com.mariemoore.patientsm.service;

import com.mariemoore.patientsm.model.Patient;
import com.mariemoore.patientsm.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(int id) {
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

    public void deletePatient(int id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Patient not found.");
        }
    }

    public void deleteAllPatients(){
        patientRepository.deleteAll();
    }
}

