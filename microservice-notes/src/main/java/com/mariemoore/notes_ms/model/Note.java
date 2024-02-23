package com.mariemoore.notes_ms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Document(collection = "patient_notes")
public class Note {

    @Id
    private String id;
    private String patientId;
    private String date;
    private String note;
}
