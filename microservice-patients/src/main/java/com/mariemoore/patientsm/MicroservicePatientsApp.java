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
		/*return args -> {
			patientService.createPatient(new Patient("1", "Test", "TestNone", new Date(45, 5, 24), "F", Arrays.asList("1 Brookside St"), "100-222-3333"));
			patientService.createPatient(new Patient("2","Test", "TestBorderline", new Date(45, 5, 24), "M", Arrays.asList("2 High St"), "200-333-4444"));
			patientService.createPatient(new Patient("3","Test", "TestInDanger", new Date(104, 5, 18), "M", Arrays.asList("3 Club Road"), "300-444-5555"));
			patientService.createPatient(new Patient("4","Test", "TestEarlyOnset", new Date(102, 5, 28), "F", Arrays.asList("4 Valley Dr"), "400-555-6666"));
			new Date("2016-05-18T16:00:00Z");
		};*/
		return args -> {
			patientService.createPatient(new Patient("1", "John", "Doe", new Date(45, 5, 24), "F", "1 Brookside St", "100-222-3333"));
			patientService.createPatient(new Patient("2","Jane", "Doe", new Date(45, 5, 24), "M", "2 High St", "200-333-4444"));
			patientService.createPatient(new Patient("3","Alice", "Smith", new Date(104, 5, 18), "M", "3 Club Road", "300-444-5555"));
			patientService.createPatient(new Patient("4","Bob", "Johnson", new Date(102, 5, 28), "F", "4 Valley Dr", "400-555-6666"));
		};
	}
}
