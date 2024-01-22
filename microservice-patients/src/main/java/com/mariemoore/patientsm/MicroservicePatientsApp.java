package com.mariemoore.patientsm;

import com.mariemoore.patientsm.model.Patient;
import com.mariemoore.patientsm.service.PatientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class MicroservicePatientsApp {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicePatientsApp.class, args);
	}


	@Bean
	public CommandLineRunner initData(PatientService patientService ){
		return args -> {
			patientService.createPatient(new Patient("1", "Test", "None", new Date(45, 5, 24), "F", Arrays.asList("1 Brookside St"), "100-222-3333"));
			patientService.createPatient(new Patient("2","Test", "Borderline", new Date(45, 5, 24), "M", Arrays.asList("2 High St"), "200-333-4444"));
			patientService.createPatient(new Patient("3","Test", "InDanger", new Date(104, 5, 18), "M", Arrays.asList("3 Club Road"), "300-444-5555"));
			patientService.createPatient(new Patient("4","Test", "EarlyOnset", new Date(102, 5, 28), "F", Arrays.asList("4 Valley Dr"), "400-555-6666"));

		};
	}
}
