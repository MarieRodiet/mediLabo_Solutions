package com.mariemoore.notes_ms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariemoore.notes_ms.model.Note;
import com.mariemoore.notes_ms.service.PatientNoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientNoteController.class)
@AutoConfigureMockMvc
public class PatientNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientNoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllNotes() throws Exception {
        Note note1 = new Note("1", "1", "first patient", "Note 1");
        Note note2 = new Note("2", "2", "second patient", "Note 2");

        List<Note> notes = Arrays.asList(note1, note2);

        when(noteService.getAllNotes()).thenReturn(notes);

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].patientId").value("1"))
                .andExpect(jsonPath("$[0].patient").value("first patient"))
                .andExpect(jsonPath("$[0].note").value("Note 1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].patientId").value("2"))
                .andExpect(jsonPath("$[1].patient").value("second patient"))
                .andExpect(jsonPath("$[1].note").value("Note 2"));
    }

    @Test
    public void testGetPatientNotesByPatientId() throws Exception {
        Note note1 = new Note("1", "1", "first patient", "Note 1");
        Note note3 = new Note("3", "1", "first patient", "Note 3");

        List<Note> notes = Arrays.asList(note1, note3);

        when(noteService.getNotesByPatientId("1")).thenReturn(notes);

        mockMvc.perform(get("/api/notes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].patientId").value("1"))
                .andExpect(jsonPath("$[0].patient").value("first patient"))
                .andExpect(jsonPath("$[0].note").value("Note 1"))
                .andExpect(jsonPath("$[1].id").value("3"))
                .andExpect(jsonPath("$[1].patientId").value("1"))
                .andExpect(jsonPath("$[1].patient").value("first patient"))
                .andExpect(jsonPath("$[1].note").value("Note 3"));
    }

    @Test
    public void testCreatePatientNote() throws Exception {
        Note note4 = new Note("4", "6", "a patient", "Note 4");

        when(noteService.createNote(any(Note.class))).thenReturn(note4);

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note4)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("4"))
                .andExpect(jsonPath("$.patientId").value("6"))
                .andExpect(jsonPath("$.patient").value("a patient"))
                .andExpect(jsonPath("$.note").value("Note 4"));
    }
}
