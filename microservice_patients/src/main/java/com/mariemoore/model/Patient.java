package com.mariemoore.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

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
    private List<String> address;
    private String phone_number;
}
