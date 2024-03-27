package com.mariemoore.notes_ms.service;

import com.mariemoore.notes_ms.model.Note;
import com.mariemoore.notes_ms.repository.PatientNoteRepository;
import com.mariemoore.notes_ms.util.HealthRiskFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientNoteService {

    @Autowired
    private PatientNoteRepository noteRepository;


    public List<Note> getAllNotes(){return noteRepository.findAll();}
    public List<Note> getNotesByPatientId(String id) {
        return noteRepository.findByPatientId(id);
    }

    public Note createNote(Note patientNote) {
        patientNote.setId(null);
        return noteRepository.save(patientNote);
    }

    public String getRiskLevelOfPatient(String patientId, String gender, String age){
        List<Note> notes = noteRepository.findByPatientId(patientId);
        return HealthRiskFinder.getHealthRiskLevel(notes, gender, age);
    }

    public void deleteAllNotes(){
        noteRepository.deleteAll();
    }

}
