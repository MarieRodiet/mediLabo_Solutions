package com.mariemoore.medilabo_solutions.model;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "patients")
public class Patient {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private Date birthdate;
    private String gender;
    private List<String> address;
    private String phone_number;
}

