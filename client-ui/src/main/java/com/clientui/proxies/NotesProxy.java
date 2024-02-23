package com.clientui.proxies;

import com.clientui.beans.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "note-gateway", url = "http://localhost:9000")
@Component
public interface NotesProxy {


    @GetMapping(value = "/api/notes")
    List<Note> getAllNotes();

    @GetMapping(value = "/api/notes/{patientId}")
    List<Note> getPatientNotesByPatientId(@PathVariable("patientId") String id);

    @PostMapping(value = "/api/notes")
    Note createPatientNote(Note note);

    @PutMapping(value = "/api/notes")
    Note updatePatientNote(Note note);

    @DeleteMapping(value = "/api/notes/{id}")
    Note deletePatientNote(@PathVariable("id") String id);
}
