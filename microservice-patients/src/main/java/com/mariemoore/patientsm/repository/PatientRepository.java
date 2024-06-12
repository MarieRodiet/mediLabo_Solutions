package com.mariemoore.patientsm.repository;

import com.mariemoore.patientsm.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, String> {

    List<Patient> findAll();
}
