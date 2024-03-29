import com.mariemoore.notes_ms.service.HealthRiskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HealthRiskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthRiskService healthRiskService;

    @BeforeEach
    void setUp() {
        // Mock behavior for healthRiskService.getRiskLevelOfPatient method
        when(healthRiskService.getRiskLevelOfPatient("patient123", "male", "30")).thenReturn("High");
    }

    @Test
    void testGetHealthRisk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/healthrisks/patient123/male/30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // You can add more assertions to verify the response body etc.
    }
}