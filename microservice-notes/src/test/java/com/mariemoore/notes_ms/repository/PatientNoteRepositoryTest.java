package com.mariemoore.notes_ms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.mariemoore.notes_ms.model.Note;
import com.mariemoore.notes_ms.service.PatientNoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes=PatientNoteRepository.class)
public class PatientNoteRepositoryTest {

    @Mock
    private PatientNoteRepository patientNoteRepositoryMock;

    @InjectMocks
    private PatientNoteService patientNoteService;

    @BeforeEach
    public void setUp() {
        Note note1 = new Note("1", "1", "TestNone", "Note 1");
        Note note2 = new Note("2", "1", "TestNone","Note 2");
        // Lenient stubbing for patientNoteRepositoryMock
        lenient().when(patientNoteRepositoryMock.findByPatientId("1")).thenReturn(Arrays.asList(note1, note2));
        lenient().when(patientNoteRepositoryMock.findByPatientId("2")).thenReturn(Arrays.asList(note2));
        lenient().when(patientNoteRepositoryMock.findByPatientId("3")).thenReturn(null);
    }

    @Test
    public void testFindByPatientId_ValidId_ReturnsListOfNotes() {
        // Test for a valid patient ID
        List<Note> notes = patientNoteService.getNotesByPatientId("1");
        assertEquals(2, notes.size());
    }

    @Test
    public void testFindByPatientId_ValidId_ReturnsSingleNote() {
        // Test for a valid patient ID with a single note
        List<Note> notes = patientNoteService.getNotesByPatientId("2");
        assertEquals(1, notes.size());
    }

    @Test
    public void testFindByPatientId_InvalidId_ReturnsNull() {
        // Test for an invalid patient ID
        List<Note> notes = patientNoteService.getNotesByPatientId("3");
        assertEquals(null, notes);
    }
}
