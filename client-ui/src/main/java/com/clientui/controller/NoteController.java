package com.clientui.controller;

import com.clientui.beans.PatientBean;
import com.clientui.proxies.NotesProxy;
import com.clientui.proxies.PatientsProxy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.clientui.beans.Note;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteController {
    @Autowired
    private NotesProxy NotesProxy;

    @Autowired
    private PatientsProxy PatientsProxy;

    @GetMapping("/patients/{id}/notes/add")
    public String addNote(@PathVariable String id, Model model){
        Note note = new Note();
        PatientBean patient = PatientsProxy.getPatient(id);
        note.setPatientId(patient.getId());
        note.setPatient(patient.getFirstname().concat(" ").concat(patient.getLastname()));
        model.addAttribute("note", note);
        return "notes/add";
    }

    @PostMapping("/patients/{id}/notes/validate")
    public String validateAddedPatient(
            @Valid Note note,
            BindingResult result,
            RedirectAttributes redirectAttributes){
        System.out.println(note);
        if (result.hasErrors()) {
            System.out.println("Note has errors: " + result.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.note", result);
            redirectAttributes.addFlashAttribute("note", note);
            redirectAttributes.addFlashAttribute("error", "Note could not be saved due to validation errors");
            return "redirect:/notes/add";
        }
        NotesProxy.createPatientNote(note);
        return "redirect:/patients";
    }
}
