package com.clientui.proxies;

import com.clientui.beans.Note;
import com.clientui.security.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "note-gateway",  url = "localhost:9002", configuration = FeignClientConfiguration.class)
@Component
public interface NotesProxy {


    @GetMapping(value = "/api/notes")
    List<Note> getAllNotes();

    @GetMapping(value = "/api/notes/{patientId}")
    List<Note> getPatientNotesByPatientId(@PathVariable("patientId") String id);

    @PostMapping(value = "/api/notes")
    Note createPatientNote(Note note);

}
