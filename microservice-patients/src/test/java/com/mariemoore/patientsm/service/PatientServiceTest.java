package com.mariemoore.patientsm.service;

import com.mariemoore.patientsm.model.Patient;
import com.mariemoore.patientsm.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepositoryMock;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void setUp() {

        Patient patient1 = new Patient("1", "Test1", "TestNone1", new Date(45, 5, 24), "F", "1 Brookside St", "100-222-1111");
        Patient patient2 = new Patient("2", "Test2", "TestNone2", new Date(45, 5, 24), "M", "1 Brookside St", "100-222-2222");

        // Lenient stubbing for patientRepositoryMock
        lenient().when(patientRepositoryMock.findAll()).thenReturn(Arrays.asList(patient1, patient2));
        lenient().when(patientRepositoryMock.findById("1")).thenReturn(Optional.of(patient1));
        lenient().when(patientRepositoryMock.save(any())).thenAnswer(invocation -> {
            Patient patient = invocation.getArgument(0);
            patient.setId("3"); // Set some dummy ID for testing
            return patient;
        });
        lenient().when(patientRepositoryMock.existsById("1")).thenReturn(true);
        lenient().when(patientRepositoryMock.existsById("2")).thenReturn(true);
        lenient().when(patientRepositoryMock.existsById("3")).thenReturn(false);
    }


    @Test
    public void testGetAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        assertEquals(2, patients.size());
    }
    @Test
    public void testGetPatientById() {
        Optional<Patient> patientOptional = patientService.getPatientById("1");
        assertTrue(patientOptional.isPresent());
        assertEquals("1", patientOptional.get().getId());

        patientOptional = patientService.getPatientById("3");
        assertFalse(patientOptional.isPresent());
    }

    @Test
    public void testCreatePatient() {
        Patient newPatient = new Patient("3", "Test3", "TestNone3", new Date(45, 5, 24), "F", "1 Brookside St", "100-222-3333");
        Patient savedPatient = patientService.createPatient(newPatient);
        assertEquals("3", savedPatient.getId());
    }

    @Test
    public void testDeletePatient() {
        assertDoesNotThrow(() -> patientService.deletePatient("1"));
        assertThrows(IllegalArgumentException.class, () -> patientService.deletePatient("3"));
    }
}
