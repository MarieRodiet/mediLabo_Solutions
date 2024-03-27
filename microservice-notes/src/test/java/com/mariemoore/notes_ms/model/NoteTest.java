package com.mariemoore.notes_ms.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void testConstructorAndGetters() {
        // Create a Note object using constructor
        Note note = new Note("1", "patientId", "firstname Lastname", "This is a note");

        // Test getter methods
        assertEquals("1", note.getId());
        assertEquals("patientId", note.getPatientId());
        assertEquals("This is a note", note.getNote());
    }

    @Test
    public void testSetters() {
        // Create a Note object
        Note note = new Note("2", "3", "third patient", "note");

        // Set values using setter methods
        note.setPatientId("4");
        note.setPatient("a different patient");
        note.setNote("This is another note");

        // Test getter methods after setting values
        assertEquals("4", note.getPatientId());
        assertEquals("a different patient", note.getPatient());
        assertEquals("This is another note", note.getNote());
    }
}
