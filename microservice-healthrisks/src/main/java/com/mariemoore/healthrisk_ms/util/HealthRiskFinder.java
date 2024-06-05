package com.mariemoore.healthrisk_ms.util;


import com.mariemoore.healthrisk_ms.model.Note;

import java.util.List;
import java.util.Objects;

public class HealthRiskFinder {
    private static final List<String> triggers = List.of(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Fumeuse",
            "Anormal",
            "Cholestérol",
            "Vertiges",
            "Rechute",
            "Réaction",
            "Anticorps"
    );

    public enum Gender{
        MAN,
        WOMAN
    }
    public static String getHealthRiskLevel(List<Note> notes, String gender, String age){
        long nbOfTriggers = notes.stream()
                .map(Note::getNote)
                .filter(Objects::nonNull)
                .filter(noteString -> triggers.contains(noteString))
                .count();
        //None : notes contain no triggers
        if(nbOfTriggers == 0){
            return HealthRiskLevels.NONE.healthRiskString;
        }
        //Borderline : notes contain 2  to 5 triggers and patient's age > 30
        else if (nbOfTriggers >= 2 && nbOfTriggers <= 5 && Integer.parseInt(age) > 30) {
            return HealthRiskLevels.BORDERLINE.healthRiskString;
        }
        /*Early onset : notes contains 5 triggers and patient is man < 30
                OR
              notes contains 7 triggers and patient is woman < 30
                OR
              notes contains 8 or more triggers and patient's age > 30 */
        else if ((nbOfTriggers == 5 && gender.equals(Gender.MAN.name()) && Integer.parseInt(age) < 30) ||
                (nbOfTriggers == 7 && gender.equals(Gender.WOMAN.name()) && Integer.parseInt(age) < 30) ||
                (nbOfTriggers >= 8 && Integer.parseInt(age) > 30)) {
            return HealthRiskLevels.EARLY_ONSET.healthRiskString;
        }
        /* In Danger : notes contain 3 triggers and patient is man and age < 30
            OR
            notes contain 4 triggers and patient is woman and age < 30
            OR
            notes contain 6 to 7 triggers and patient is man and age > 30*/
        else if ((nbOfTriggers >= 3 && gender.equals(Gender.MAN.name()) && Integer.parseInt(age) < 30) ||
                (nbOfTriggers >= 4 && gender.equals(Gender.WOMAN.name()) && Integer.parseInt(age) < 30) ||
                ((nbOfTriggers >= 6 && nbOfTriggers <= 7) && gender.equals(Gender.MAN.name()) && Integer.parseInt(age) > 30)) {
            return HealthRiskLevels.IN_DANGER.healthRiskString;
        } else {
            return HealthRiskLevels.UNKNOWN.healthRiskString;
        }
    }
}
