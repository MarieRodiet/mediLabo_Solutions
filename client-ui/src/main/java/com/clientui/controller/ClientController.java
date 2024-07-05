package com.clientui.controller;


import com.clientui.beans.Note;
import com.clientui.beans.PatientBean;
import com.clientui.proxies.HealthRiskProxy;
import com.clientui.proxies.PatientsProxy;
import com.clientui.proxies.NotesProxy;
import com.clientui.security.TokenManager;
import com.clientui.util.GetAge;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * Controller for handling all patient-related interactions within the UI.
 * This includes listing patients, viewing patient details, adding, updating, and deleting patient records.
 */
@Slf4j
@Controller
public class ClientController {
    @Autowired
    private PatientsProxy PatientsProxy;

    @Autowired
    private NotesProxy NotesProxy;

    @Autowired
    private HealthRiskProxy HealthRiskProxy;


    @Autowired
    private TokenManager tokenManager;
    private HttpHeaders headers = new HttpHeaders();

    /**
     * Displays a list of all patients.
     * @param model The Model object to pass data to the view.
     * @param request The HttpServletRequest object to read the HTTP request.
     * @return The view name of the patient list page.
     */
    @GetMapping("/patients")
    public String patientList(Model model, HttpServletRequest request){
        tokenManager.addTokenToHeaders(request, headers);
        List<PatientBean> patients =  PatientsProxy.getAllPatients();
        log.info("Retrieved patient list successfully");
        model.addAttribute("patients", patients);
        return "patients/list";
    }

    /**
     * Displays detailed information for a specific patient.
     * @param id The patient's unique identifier.
     * @param model The Model object to pass data to the view.
     * @param request The HttpServletRequest object to read the HTTP request.
     * @return The view name of the patient details page.
     */
    @GetMapping("/patients/{id}")
    public String checkPatient(@PathVariable String id, Model model, HttpServletRequest request){
        tokenManager.addTokenToHeaders(request, headers);
        PatientBean patient = PatientsProxy.getPatient(id);
        List<Note> notes = NotesProxy.getPatientNotesByPatientId(id);
        Integer age = GetAge.calculateAge(patient.getBirthdate());
        String healthRiskLevel = HealthRiskProxy.getHealthRiskLevel("", id, patient.getGender(), String.valueOf(age));
        log.info("Retrieved patient details successfully");
        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        model.addAttribute("healthRiskLevel", healthRiskLevel);
        return "patients/patient";
    }

    /**
     * Directs to the form to add a new patient.
     * @param model The Model object to pass data to the view.
     * @param request The HttpServletRequest object to read the HTTP request.
     * @return The view name for adding a patient.
     */
    @GetMapping("/patients/add")
    public String addPatient(Model model, HttpServletRequest request){
        log.info("Redirected to add patient page");
        tokenManager.addTokenToHeaders(request, headers);
        PatientBean patient = new PatientBean();
        model.addAttribute("patient", patient);
        return "patients/add";
    }

    /**
     * Validates and saves a new or updated patient record.
     * @param patientBean The patient data from the form.
     * @param result BindingResult that captures validation errors.
     * @param redirectAttributes Attributes for redirect scenarios.
     * @param request The HttpServletRequest object to read the HTTP request.
     * @return Redirects to the patient list view if successful, or back to the form if there are validation errors.
     */
    @PostMapping("/patients/validate")
    public String validateAddedPatient(
            @Valid PatientBean patientBean,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request){
        if (result.hasErrors()) {
            tokenManager.addTokenToHeaders(request, headers);
            log.error("Patient validation failed: {}", result.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientBean", result);
            redirectAttributes.addFlashAttribute("patientBean", patientBean);
            redirectAttributes.addFlashAttribute("error", "Patient could not be saved due to validation errors");
            return "redirect:/patients/add";
        }
        if (StringUtils.isEmpty(patientBean.getId())) {
            PatientsProxy.createPatient(patientBean);
            log.info("New patient created successfully");
        } else {
            PatientsProxy.updatePatient(patientBean);
            log.info("Patient details updated successfully");
        }
        return "redirect:/patients";
    }

    /**
     * Directs to the form for editing an existing patient record.
     * @param id The unique identifier of the patient to edit.
     * @param model The Model object to pass data to the view.
     * @param request The HttpServletRequest object to read the HTTP request.
     * @return The view name for adding/editing a patient, reused the add patient view.
     */
    @GetMapping("/patients/edit/{id}")
    public String editPatient(@PathVariable String id, Model model, HttpServletRequest request){
        log.info("Redirected to edit patient page");
        tokenManager.addTokenToHeaders(request, headers);
        PatientBean patient = PatientsProxy.getPatient(id);
        model.addAttribute("patient", patient);
        return "patients/add";
    }

    /**
     * Handles the request to delete a patient record.
     * @param id The unique identifier of the patient to delete.
     * @param model The Model object to pass data to the view.
     * @param request The HttpServletRequest object to read the HTTP request.
     * @return Redirects to the patient list view after deletion.
     */
    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable String id, Model model, HttpServletRequest request){
        tokenManager.addTokenToHeaders(request, headers);
        PatientsProxy.deletePatient(id);
        log.info("Patient deleted successfully");
        List<PatientBean> patients =  PatientsProxy.getAllPatients();
        model.addAttribute("patients", patients);
        return "redirect:/patients";
    }

}
