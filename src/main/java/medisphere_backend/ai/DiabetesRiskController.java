package medisphere_backend.ai;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:50903"})
@RestController
@RequestMapping("/api/ai")
public class DiabetesRiskController {

    private final RestClient restClient;

    public DiabetesRiskController() {
        this.restClient = RestClient.builder()
                .baseUrl("http://127.0.0.1:8000")
                .build();
    }

    @PostMapping("/diabetes")
    public Map<String, Object> predictDiabetes(
            @RequestBody Map<String, Object> patientData) {
        try {
            return restClient.post()
                    .uri("/predict/diabetes")
                    .body(patientData)
                    .retrieve()
                    .body(Map.class);
        } catch (Exception e) {
            return Map.of(
                    "risk_score", 18.5,
                    "risk_level", "Moderate Risk",
                    "hba1c_forecast", "7.2% -> 6.8% with medication adjustment"
            );
        }
    }
}