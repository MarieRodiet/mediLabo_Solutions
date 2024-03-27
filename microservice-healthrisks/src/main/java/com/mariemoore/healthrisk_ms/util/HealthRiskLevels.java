package com.mariemoore.healthrisk_ms.util;

public enum HealthRiskLevels {
    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In Danger"),
    EARLY_ONSET("Early Onset"),
    UNKNOWN("Unknown Risk Level");

    public final String healthRiskString;
    HealthRiskLevels(String s) {
        this.healthRiskString = s;
    }
}
