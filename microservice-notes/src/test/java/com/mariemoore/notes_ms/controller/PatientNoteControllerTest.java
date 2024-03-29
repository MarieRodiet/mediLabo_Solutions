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

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientNoteService noteService;

    @BeforeEach
    void setUp() {
        // Mocking behavior for service methods
        List<Note> mockNotes = new ArrayList<>();
        Note note1 = new Note();
        note1.setId(1L);
        note1.setPatientId("patient123");
        // Set other properties for note1
        mockNotes.add(note1);

        // Mock behavior for getAllNotes
        when(noteService.getAllNotes()).thenReturn(mockNotes);

        // Mock behavior for getNotesByPatientId
        when(noteService.getNotesByPatientId("patient123")).thenReturn(mockNotes);
    }

    @Test
    void testGetAllNotes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetNotesByPatientId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/patient123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreateNote() throws Exception {
        Note note = new Note();
        // Set properties for the note object

        mockMvc.perform(MockMvcRequestBuilders.post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // You can add more assertions to verify the response body etc.
    }

    @Test
    void testGetHealthRisk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/healthrisks/patient123/male/30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // You can add more assertions to verify the response body etc.
    }
}
