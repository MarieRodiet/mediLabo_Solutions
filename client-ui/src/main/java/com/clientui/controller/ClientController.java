package com.clientui.controller;


import com.clientui.beans.PatientBean;
import com.clientui.proxies.MicroservicePatientsProxy;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class ClientController {
    @Autowired
    private MicroservicePatientsProxy PatientsProxy;


    @GetMapping("/patients")
    public String patientList(Model model){
        List<PatientBean> patients =  PatientsProxy.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients/list";
    }

    @GetMapping("/patients/{id}")
    public String checkPatient(@PathVariable int id, Model model){
        PatientBean patient = PatientsProxy.getPatient(id);
        model.addAttribute("patient", patient);
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
            Model model,
            @Valid PatientBean patientBean,
            BindingResult result,
            RedirectAttributes redirectAttributes){
        if (!result.hasErrors()) {
            PatientsProxy.createPatient(patientBean);
            return "redirect:/patients";
        }
        System.out.println("Patient has error");
        redirectAttributes.addFlashAttribute("error", "Patient could not get saved");
        return "redirect:/patients/add";

    }

    @PostMapping("/patients/edit/{id}")
    public String editPatient(@PathVariable int id, Model model){
        PatientBean patient = PatientsProxy.getPatient(id);
        model.addAttribute("patient", patient);
        return "patients/add";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable int id, Model model){
        PatientsProxy.deletePatient(id);
        List<PatientBean> patients =  PatientsProxy.getAllPatients();
        model.addAttribute("patients", patients);
        return "redirect:/patients";
    }

}
