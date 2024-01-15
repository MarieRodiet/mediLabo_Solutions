package com.mariemoore.medilabo_solutions.repository;

import com.mariemoore.medilabo_solutions.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
}