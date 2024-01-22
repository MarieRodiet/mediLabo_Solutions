package com.clientui.controller;


import com.clientui.beans.PatientBean;
import com.clientui.proxies.MicroservicePatientsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
public class ClientController {
    @Autowired
    private MicroservicePatientsProxy PatientsProxy;


    @RequestMapping("/")
    public String patientDashboard(Model model){
        List<PatientBean> patients =  PatientsProxy.listeDesPatients();
        model.addAttribute("patients", patients);
        return "DashboardPatients";
    }

    @RequestMapping("/details-patient/{id}")

    public String fichePatient(@PathVariable int id, Model model){
        PatientBean patient = PatientsProxy.recupererUnPatient(id);
        model.addAttribute("patient", patient);
        return "FichePatient";
    }
}
