package com.mariemoore.patientsm.repository;

import com.mariemoore.patientsm.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findAll();
}
