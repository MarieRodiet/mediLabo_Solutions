package com.clientui.controller;


import com.clientui.beans.Note;
import com.clientui.beans.PatientBean;
import com.clientui.proxies.PatientsProxy;
import com.clientui.proxies.NotesProxy;
import com.clientui.util.GetAge;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class ClientController {
    @Autowired
    private PatientsProxy PatientsProxy;

    @Autowired
    private NotesProxy NotesProxy;



    @GetMapping("/patients")
    public String patientList(Model model){
        List<PatientBean> patients =  PatientsProxy.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients/list";
    }

    @GetMapping("/patients/{id}")
    public String checkPatient(@PathVariable String id, Model model){
        PatientBean patient = PatientsProxy.getPatient(id);
        List<Note> notes = NotesProxy.getPatientNotesByPatientId(id);
        Integer age = GetAge.calculateAge(patient.getBirthdate());
        String healthRiskLevel = NotesProxy.getHealthRiskLevel(id, patient.getGender(), String.valueOf(age));
        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        model.addAttribute("healthRiskLevel", healthRiskLevel);
        return "patients/patient";
    }

    @GetMapping("/patients/add")
    public String addPatient(Model model){
        PatientBean patient = new PatientBean();
        model.addAttribute("patient", patient);
        return "patients/add";
    }

    @PostMapping("/patients/validate")
    public String validateAddedPatient(
            @Valid PatientBean patientBean,
            BindingResult result,
            RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            System.out.println("Patient has errors: " + result.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientBean", result);
            redirectAttributes.addFlashAttribute("patientBean", patientBean);
            redirectAttributes.addFlashAttribute("error", "Patient could not be saved due to validation errors");
            return "redirect:/patients/add";
        }
        if (StringUtils.isEmpty(patientBean.getId())) {
            // Create new patient
            PatientsProxy.createPatient(patientBean);
        } else {
            // Update existing patient
            PatientsProxy.updatePatient(patientBean);
        }
        return "redirect:/patients";
    }

    @GetMapping("/patients/edit/{id}")
    public String editPatient(@PathVariable String id, Model model){
        PatientBean patient = PatientsProxy.getPatient(id);
        model.addAttribute("patient", patient);
        return "patients/add";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable String id, Model model){
        PatientsProxy.deletePatient(id);
        List<PatientBean> patients =  PatientsProxy.getAllPatients();
        model.addAttribute("patients", patients);
        return "redirect:/patients";
    }

}
