package com.mariemoore.notes_ms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariemoore.notes_ms.model.Note;
import com.mariemoore.notes_ms.service.PatientNoteService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientNoteController.class)
@AutoConfigureMockMvc
public class PatientNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientNoteService noteService;

    @InjectMocks
    private PatientNoteController patientNoteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllNotes() throws Exception {
        // Given
        Note note1 = new Note("99", "4", "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction");
        Note note2 = new Note("100", "4", "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction");

        List<Note> notes = Arrays.asList(note1, note2);
        when(noteService.getAllNotes()).thenReturn(notes);

        //When
        ResponseEntity<List<Note>> responseEntity = patientNoteController.getAllNotes();

        // Then
        assertEquals(notes, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetNotesByPatientId() throws Exception {
        // Given
        String patientId = "1";
        Note note1 = new Note("99", "4", "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction");
        Note note2 = new Note("100", "4", "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction");

        List<Note> notes = Arrays.asList(note1, note2);
        when(noteService.getNotesByPatientId(patientId)).thenReturn(notes);

        // When
        ResponseEntity<List<Note>> responseEntity = patientNoteController.getNotesByPatientId(patientId);

        // Then
        assertEquals(notes, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testCreateNote() throws Exception {
        // Given
        Note note = new Note("99", "4", "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction");
        when(noteService.createNote(note)).thenReturn(note);

        // When
        ResponseEntity<Note> responseEntity = patientNoteController.createNote(note);

        // Then
        assertEquals(note, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


}
