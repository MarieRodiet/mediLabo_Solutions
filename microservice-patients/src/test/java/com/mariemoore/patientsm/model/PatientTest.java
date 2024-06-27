package com.mariemoore.patientsm.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientTest {
    @Test
    public void testConstructorAndGetters() {
        // Create a Patient object using constructor
        Patient patient1 = new Patient(1, "Test", "TestNone", new Date(45, 5, 24), "F", "1 Brookside St", "100-222-3333");
        // Test getter methods
        assertEquals("1", patient1.getId());
        assertEquals("Test", patient1.getFirstname());
        assertEquals("1 Brookside St", patient1.getAddress());
    }

    @Test
    public void testSetters() {
        // Create a Patient object
        Patient patient1 = new Patient(1, "Test", "TestNone", new Date(45, 5, 24), "F", "1 Brookside St", "100-222-3333");


        // Set values using setter methods
        patient1.setFirstname("Changed");
        patient1.setLastname("Changed");
        patient1.setAddress("A different address");

        // Test getter methods after setting values
        assertEquals("Changed", patient1.getFirstname());
        assertEquals("Changed", patient1.getLastname());
        assertEquals("A different address", patient1.getAddress());
    }
}
