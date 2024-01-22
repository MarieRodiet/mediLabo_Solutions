package com.clientui.beans;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
public class PatientBean {

    private String id;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String gender;
    private List<String> address;
    private String phone_number;


}
