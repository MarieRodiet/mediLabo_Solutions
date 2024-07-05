package com.mariemoore.notes_ms.controller;

import com.mariemoore.notes_ms.model.Note;
import com.mariemoore.notes_ms.service.PatientNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing notes associated with patients.
 * This controller handles CRUD operations on notes and fetches health risk assessments for patients.
 */
@Slf4j
@RestController
@RequestMapping("/api/notes")
public class PatientNoteController {

    @Autowired
    private PatientNoteService noteService;

    /**
     * Retrieves all notes in the system.
     *
     * @return A ResponseEntity containing a list of all notes.
     */
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(){
        log.info("GET /api/notes/ - Getting all notes");
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    /**
     * Retrieves all notes for a specific patient identified by their ID.
     *
     * @param patientId The ID of the patient whose notes are being requested.
     * @return A ResponseEntity containing a list of notes for the specified patient.
     */
    @GetMapping("/{patientId}")
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable String patientId){
        log.info("GET /api/notes/{} - Getting all notes of Patient with ID: {}", patientId, patientId);
        List<Note> notes = noteService.getNotesByPatientId(patientId);
        return ResponseEntity.ok(notes);
    }

    /**
     * Creates a new note in the system.
     *
     * @param note The note to be created.
     * @return A ResponseEntity containing the newly created note, or an error if creation fails.
     */
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        log.info("POST /api/notes - Creating a new note: {}", note);
        try {
            Note createdNote = noteService.createNote(note);
            return ResponseEntity.ok(createdNote);
        } catch (Exception e) {
            log.error("Error creating note: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves the health risk level of a patient based on their ID, gender, and age.
     *
     * @param patientId The ID of the patient.
     * @param gender The gender of the patient.
     * @param age The age of the patient.
     * @return A ResponseEntity containing the health risk level as a string.
     */
    @GetMapping("/healthrisks/{patientId}/{gender}/{age}")
    public ResponseEntity<String> getHealthRisk(@PathVariable String patientId, @PathVariable String gender, @PathVariable String age){
        log.info("GET /api/notes/healthrisks/{} - Getting health risks of Patient with ID: {}", patientId, patientId);
        String healthRiskLevel = noteService.getRiskLevelOfPatient(patientId, gender, age);
        return ResponseEntity.ok(healthRiskLevel);
    }

}
