package com.mariemoore.patientsm.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@Data
@Document(collection = "patients")
public class Patient {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String gender;
    private String address;
    private String phone_number;
}

