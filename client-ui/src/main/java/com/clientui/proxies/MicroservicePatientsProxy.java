package com.clientui.proxies;

import com.clientui.beans.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "gateway", url = "localhost:9001")
@Component
public interface MicroservicePatientsProxy {
    @GetMapping(value = "/api/patients")
    List<PatientBean> listeDesPatients();

    @GetMapping( value = "/api/patients/{id}")
    PatientBean recupererUnPatient(@PathVariable("id") int id);

}
