import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PatientNoteRepositoryTest {

    @Mock
    private PatientNoteRepository patientNoteRepositoryMock;

    @InjectMocks
    private PatientNoteService patientNoteService;

    @BeforeEach
    public void setUp() {
        // Mocking behavior of PatientNoteRepository
        Note note1 = new Note("1", "Note 1");
        Note note2 = new Note("2", "Note 2");

        List<Note> notes = Arrays.asList(note1, note2);

        when(patientNoteRepositoryMock.findByPatientId("1")).thenReturn(notes);
        when(patientNoteRepositoryMock.findByPatientId("2")).thenReturn(Arrays.asList(note2));
        when(patientNoteRepositoryMock.findByPatientId("3")).thenReturn(null);
    }

    @Test
    public void testFindByPatientId_ValidId_ReturnsListOfNotes() {
        // Test for a valid patient ID
        List<Note> notes = patientNoteService.getNotesByPatientId("1");
        assertEquals(2, notes.size());
    }

    @Test
    public void testFindByPatientId_ValidId_ReturnsSingleNote() {
        // Test for a valid patient ID with a single note
        List<Note> notes = patientNoteService.getNotesByPatientId("2");
        assertEquals(1, notes.size());
    }

    @Test
    public void testFindByPatientId_InvalidId_ReturnsNull() {
        // Test for an invalid patient ID
        List<Note> notes = patientNoteService.getNotesByPatientId("3");
        assertEquals(null, notes);
    }
}