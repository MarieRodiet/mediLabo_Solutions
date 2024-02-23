package com.mariemoore.notes_ms.controller;

import com.mariemoore.notes_ms.model.Note;
import com.mariemoore.notes_ms.service.PatientNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/notes")
public class PatientNoteController {

    @Autowired
    private PatientNoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes(){
        log.info("GET /api/notes/ - Getting all notes");
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Note>> getPatientNotesByPatientId(@PathVariable String id){
        log.info("GET /api/notes/{} - Getting all notes of Patient with ID: {}", id, id);
        List<Note> notes = noteService.getNotesByPatientId(id);
        return ResponseEntity.ok(notes);
    }

    @PostMapping
    public ResponseEntity<Note> createPatientNote(@RequestBody Note note) {
        log.info("POST /api/notes - Creating a new note: {}", note);
        try {
            Note createdNote = noteService.createNote(note);
            return ResponseEntity.ok(createdNote);
        } catch (Exception e) {
            log.error("Error creating note: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Note> updatePatientNote(@RequestBody Note updatedNote) {
        log.info("PUT /api/notes - Updating note: {}", updatedNote);
        try {
            Note updated = noteService.updateNote(updatedNote);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            log.error("Error updating note: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Note>> deletePatientNote(@PathVariable String id) {
        log.info("DELETE /api/notes/{} - Deleting note by ID: {}", id, id);
        Optional<Note> note = noteService.getNoteById(id);
        if(note != null){
            noteService.deleteNote(id);
        }
        return ResponseEntity.ok(note);
    }
}
