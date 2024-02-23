package com.mariemoore.notes_ms.repository;

import com.mariemoore.notes_ms.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PatientNoteRepository extends MongoRepository<Note, String> {
    List<Note> findByPatientId(String id);
}
