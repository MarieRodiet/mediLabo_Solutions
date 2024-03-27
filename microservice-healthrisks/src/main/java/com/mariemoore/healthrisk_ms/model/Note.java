package com.mariemoore.healthrisk_ms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Note {

    private String id;
    private String patientId;
    private String patient;
    private String note;

}
