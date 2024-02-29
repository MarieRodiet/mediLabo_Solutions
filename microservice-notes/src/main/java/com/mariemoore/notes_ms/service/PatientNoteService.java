package com.mariemoore.notes_ms.service;

import com.mariemoore.notes_ms.model.Note;
import com.mariemoore.notes_ms.repository.PatientNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientNoteService {

    @Autowired
    private PatientNoteRepository noteRepository;

    public List<Note> getAllNotes(){return noteRepository.findAll();}
    public List<Note> getNotesByPatientId(String id) {
        return noteRepository.findByPatientId(id);
    }

    public Optional<Note> getNoteById(String id){
        return noteRepository.findById(id);
    }

    public Note createNote(Note patientNote) {
        patientNote.setId(null);
        return noteRepository.save(patientNote);
    }
}
