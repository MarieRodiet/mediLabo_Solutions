package com.mariemoore.patientsm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariemoore.patientsm.model.Patient;
import com.mariemoore.patientsm.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc
public class PatientControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;


    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPatients() {
        // Given
        Patient patient1 = new Patient("1", "Test1", "TestNone1", new Date(45, 5, 24), "F", "1 Brookside St first", "100-222-1111");
        Patient patient2 = new Patient("2", "Test2", "TestNone2", new Date(45, 5, 24), "M", "1 Brookside St second", "100-222-2222");

        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientService.getAllPatients()).thenReturn(patients);

        // When
        ResponseEntity<List<Patient>> responseEntity = patientController.getAllPatients();

        // Then
        assertEquals(patients, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetPatientById() {
        // Given
        String id = "1";
        Patient patient = new Patient("1", "Test1", "TestNone1", new Date(45, 5, 24), "F", "1 Brookside St first", "100-222-1111");
        when(patientService.getPatientById(id)).thenReturn(Optional.of(patient));

        // When
        ResponseEntity<Patient> responseEntity = patientController.getPatientById(id);

        // Then
        assertEquals(patient, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetPatientById_NotFound() {
        // Given
        String id = "100";
        when(patientService.getPatientById(id)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Patient> responseEntity = patientController.getPatientById(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreatePatient() {
        // Given
        Patient patient = new Patient("1", "Test1", "TestNone1", new Date(45, 5, 24), "F", "1 Brookside St first", "100-222-1111");
        when(patientService.createPatient(patient)).thenReturn(patient);

        // When
        ResponseEntity<Patient> responseEntity = patientController.createPatient(patient);

        // Then
        assertEquals(patient, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdatePatient() {
        // Given
        Patient updatedPatient = new Patient("1", "Test1", "TestNone1", new Date(45, 5, 24), "F", "1 Brookside St first", "100-222-1111");

        when(patientService.updatePatient(updatedPatient)).thenReturn(updatedPatient);

        // When
        ResponseEntity<Patient> responseEntity = patientController.updatePatient(updatedPatient);

        // Then
        assertEquals(updatedPatient, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void testDeletePatient() {
        // Given
        String id = "1";
        Patient toBeDeleted = new Patient("1", "Test1", "TestNone1", new Date(45, 5, 24), "F", "1 Brookside St first", "100-222-1111");

        Optional<Patient> patient = Optional.of(toBeDeleted);
        when(patientService.getPatientById(id)).thenReturn(patient);

        // When
        ResponseEntity<Optional<Patient>> responseEntity = patientController.deletePatient(id);

        // Then
        assertTrue(responseEntity.getBody().isPresent());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(patientService, times(1)).deletePatient(id);
    }

    @Test
    void testDeletePatient_NotFound() {
        // Given
        String id = "100";
        when(patientService.getPatientById(id)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Optional<Patient>> responseEntity = patientController.deletePatient(id);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
