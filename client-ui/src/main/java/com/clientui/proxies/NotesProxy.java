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

    @GetMapping(value = "/api/notes/healthrisks/{patientId}/{gender}/{age}")
    String getHealthRiskLevel(@PathVariable("patientId")String id, @PathVariable("gender")String gender, @PathVariable("age")String age);
}
