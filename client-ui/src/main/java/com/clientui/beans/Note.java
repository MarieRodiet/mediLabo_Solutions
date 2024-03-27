package com.clientui.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Note {
    private String id;
    private String patientId;
    private String patient;
    private String note;
}
