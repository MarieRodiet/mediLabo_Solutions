package com.clientui.controller;

import com.clientui.beans.PatientBean;
import com.clientui.proxies.NotesProxy;
import com.clientui.proxies.PatientsProxy;
import com.clientui.security.TokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.clientui.beans.Note;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller responsible for managing notes related to patients.
 * Handles creation and validation of notes linked to specific patients.
 */
@Slf4j
@Controller
public class NoteController {
    @Autowired
    private NotesProxy NotesProxy;

    @Autowired
    private PatientsProxy PatientsProxy;

    @Autowired
    private TokenManager tokenManager;
    private HttpHeaders headers = new HttpHeaders();

    /**
     * Presents the form to add a new note for a specified patient.
     *
     * @param id The unique identifier of the patient to whom the note will be added.
     * @param model The Model object to pass data to the view.
     * @param request The HttpServletRequest object to read the HTTP request.
     * @return The view name for the note addition form.
     */
    @GetMapping("/patients/{id}/notes/add")
    public String addNote(@PathVariable String id, Model model, HttpServletRequest request){
        tokenManager.addTokenToHeaders(request, headers);
        Note note = new Note();
        PatientBean patient = PatientsProxy.getPatient(id);
        note.setPatientId(patient.getId());
        note.setPatient(patient.getFirstname().concat(" ").concat(patient.getLastname()));
        model.addAttribute("note", note);
        log.info("Redirected to add note page for patient with ID {}", id);
        return "notes/add";
    }

    /**
     * Validates and saves a newly added note. Redirects to the patient list if successful, or back to the form if validation fails.
     *
     * @param note The Note object populated from the form data.
     * @param result The BindingResult that captures validation errors.
     * @param redirectAttributes Attributes for redirect scenarios, used to pass forward errors.
     * @param request The HttpServletRequest object to read the HTTP request.
     * @return A redirection string to either the patient list or back to the note addition form.
     */
    @PostMapping("/patients/{id}/notes/validate")
    public String validateAddedPatient(
            @Valid Note note,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request){
        tokenManager.addTokenToHeaders(request, headers);
        if (result.hasErrors()) {
            log.error("Note validation failed: {}", result.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.note", result);
            redirectAttributes.addFlashAttribute("note", note);
            redirectAttributes.addFlashAttribute("error", "Note could not be saved due to validation errors");
            return "redirect:/notes/add";
        }
        log.info("New note added for patient with ID {}", note.getPatientId());
        NotesProxy.createPatientNote(note);
        return "redirect:/patients";
    }
}
