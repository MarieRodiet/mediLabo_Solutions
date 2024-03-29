package com.mariemoore.notes_ms.service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import com.mariemoore.notes_ms.model.Note;
import com.mariemoore.notes_ms.repository.PatientNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientNoteServiceTest {

    @Mock
    private PatientNoteRepository noteRepositoryMock;

    @InjectMocks
    private PatientNoteService patientNoteService;

    @BeforeEach
    public void setUp() {
        // Lenient stubbing for noteRepositoryMock
        lenient().when(noteRepositoryMock.findAll()).thenReturn(Arrays.asList(new Note("1", "patient1", "2024-03-18", "Note 1"),
                new Note("2", "patient2", "2024-03-19", "Note 2")));
        lenient().when(noteRepositoryMock.findByPatientId("1")).thenReturn(Arrays.asList(new Note("1", "patient1", "2024-03-18", "Note 1")));
        lenient().when(noteRepositoryMock.findById("1")).thenReturn(Optional.of(new Note("1", "patient1", "2024-03-18", "Note 1")));
        lenient().when(noteRepositoryMock.existsById("1")).thenReturn(true);
        lenient().when(noteRepositoryMock.existsById("3")).thenReturn(false);
    }

    @Test
    public void testGetAllNotes() {
        List<Note> notes = patientNoteService.getAllNotes();
        assertEquals(2, notes.size());
    }

    @Test
    public void testGetNotesByPatientId() {
        List<Note> notes = patientNoteService.getNotesByPatientId("1");
        assertEquals(1, notes.size());
    }

    @Test
    public void testCreateNote() {
        Note newNote = new Note("3", "patient3", "2024-03-20", "New Note");
        when(noteRepositoryMock.save(newNote)).thenReturn(newNote);
        Note createdNote = patientNoteService.createNote(newNote);
        assertEquals("patient3", createdNote.getPatientId());
    }
}
