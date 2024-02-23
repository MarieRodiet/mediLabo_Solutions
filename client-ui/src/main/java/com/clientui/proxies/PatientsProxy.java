package com.clientui.proxies;

import com.clientui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "patient-gateway", url = "localhost:9001")
@Component
public interface PatientsProxy {
    @GetMapping(value = "/api/patients")
    List<PatientBean> getAllPatients();

    @GetMapping( value = "/api/patients/{id}")
    PatientBean getPatient(@PathVariable("id") String id);

    @PostMapping(value= "/api/patients")
    PatientBean createPatient(@RequestBody PatientBean patientBean);

    @PutMapping(value= "/api/patients")
    PatientBean updatePatient(@RequestBody PatientBean patientBean);

    @DeleteMapping( value = "/api/patients/{id}")
    PatientBean deletePatient(@PathVariable("id") String id);

}
