import com.mariemoore.healthrisk_ms.model.Note;
import com.mariemoore.healthrisk_ms.util.HealthRiskFinder;
import com.mariemoore.healthrisk_ms.util.HealthRiskLevels;
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
        notes.add(new Note("Hémoglobine A1C"));
        notes.add(new Note("Microalbumine"));
        String borderlineRiskLevel = HealthRiskFinder.getHealthRiskLevel(notes, "WOMAN", "35");
        assertEquals(HealthRiskLevels.BORDERLINE.healthRiskString, borderlineRiskLevel);

        // Test case for EARLY_ONSET health risk level
        notes.add(new Note("Taille"));
        notes.add(new Note("Poids"));
        notes.add(new Note("Fumeur"));
        notes.add(new Note("Anormal"));
        String earlyOnsetRiskLevel = HealthRiskFinder.getHealthRiskLevel(notes, "MAN", "28");
        assertEquals(HealthRiskLevels.EARLY_ONSET.healthRiskString, earlyOnsetRiskLevel);

        // Test case for IN_DANGER health risk level
        notes.add(new Note("Cholestérol"));
        String inDangerRiskLevel = HealthRiskFinder.getHealthRiskLevel(notes, "WOMAN", "25");
        assertEquals(HealthRiskLevels.IN_DANGER.healthRiskString, inDangerRiskLevel);

        // Test case for UNKNOWN health risk level
        notes.add(new Note("Vertiges"));
        String unknownRiskLevel = HealthRiskFinder.getHealthRiskLevel(notes, "MAN", "35");
        assertEquals(HealthRiskLevels.UNKNOWN.healthRiskString, unknownRiskLevel);
    }

    @Test
    void testAllHealthRiskLevels() {
        // Iterate over all HealthRiskLevels enum values
        for (HealthRiskLevels level : HealthRiskLevels.values()) {
            List<Note> notes = new ArrayList<>();
            String expectedHealthRiskString = level.healthRiskString;

            // Create a test case with appropriate notes and patient information
            switch (level) {
                case NONE:
                    // No triggers, any gender or age
                    break;
                case BORDERLINE:
                    // 2-5 triggers, patient age > 30
                    notes.add(new Note("Hémoglobine A1C"));
                    notes.add(new Note("Microalbumine"));
                    break;
                case EARLY_ONSET:
                    // 5 triggers, male patient < 30
                    notes.add(new Note("Taille"));
                    notes.add(new Note("Poids"));
                    notes.add(new Note("Fumeur"));
                    notes.add(new Note("Anormal"));
                    break;
                case IN_DANGER:
                    // 3 triggers, male patient < 30
                    notes.add(new Note("Cholestérol"));
                    notes.add(new Note("Fumeur"));
                    notes.add(new Note("Anormal"));
                    break;
                case UNKNOWN:
                    // Any other scenario
                    notes.add(new Note("Random Trigger"));
                    break;
            }

            // Call the getHealthRiskLevel method and verify the result
            String actualHealthRiskString = HealthRiskFinder.getHealthRiskLevel(notes, "MAN", "25");
            assertEquals(expectedHealthRiskString, actualHealthRiskString);
        }
    }
}
