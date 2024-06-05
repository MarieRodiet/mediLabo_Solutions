package com.clientui.proxies;

import com.clientui.security.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "healthrisk-gateway",  url = "localhost:9002", configuration = FeignClientConfiguration.class)
@Component
public interface HealthRiskProxy {
    @GetMapping(value = "/api/healthrisks/{patientId}/{gender}/{age}")
    String getHealthRiskLevel(
            @RequestHeader("Authorization") String token,
            @PathVariable("patientId")String id,
            @PathVariable("gender")String gender,
            @PathVariable("age")String age);
}
