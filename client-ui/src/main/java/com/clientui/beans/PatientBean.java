package com.clientui.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientBean {

    private String id;
    private String firstname;
    private String lastname;
    @DateTimeFormat(pattern = "E MMM dd HH:mm:ss z yyyy")
    private Date birthdate;
    private String gender;
    private String address;
    private String phone_number;
}
