package com.clientui.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientBean {

    private int id;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String gender;
    private List<String> address;
    private String phone_number;
}
