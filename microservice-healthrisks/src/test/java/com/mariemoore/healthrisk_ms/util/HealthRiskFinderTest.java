package com.mariemoore.healthrisk_ms.util;

import com.mariemoore.healthrisk_ms.model.Note;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthRiskFinderTest {

    @Test
    void testGetHealthRiskLevel() {
        List<Note> notes = new ArrayList<>();

        // Test case for NONE health risk level
        String noneRiskLevel = HealthRiskFinder.getHealthRiskLevel(notes, "MAN", "25");
        assertEquals(HealthRiskLevels.NONE.healthRiskString, noneRiskLevel);

        // Test case for BORDERLINE health risk level
        notes.add(new Note("id", "patientId", "patient","Hémoglobine A1C"));
        notes.add(new Note("id", "patientId", "patient","Microalbumine"));
        String borderlineRiskLevel = HealthRiskFinder.getHealthRiskLevel(notes, "WOMAN", "35");
        assertEquals(HealthRiskLevels.BORDERLINE.healthRiskString, borderlineRiskLevel);

        // Test case for EARLY_ONSET health risk level
        notes.add(new Note("id", "patientId", "patient","Taille"));
        notes.add(new Note("id", "patientId", "patient","Poids"));
        notes.add(new Note("id", "patientId", "patient","Fumeur"));
        String earlyOnsetRiskLevel = HealthRiskFinder.getHealthRiskLevel(notes, "MAN", "28");
        assertEquals(HealthRiskLevels.EARLY_ONSET.healthRiskString, earlyOnsetRiskLevel);

        // Test case for IN_DANGER health risk level
        notes.add(new Note("id", "patientId", "patient","Cholestérol"));
        String inDangerRiskLevel = HealthRiskFinder.getHealthRiskLevel(notes, "WOMAN", "25");
        assertEquals(HealthRiskLevels.IN_DANGER.healthRiskString, inDangerRiskLevel);

    }

}
