package medisphere_backend.ai;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:50903"})
@RestController
@RequestMapping("/api/ai")
public class CvdRiskController {

    private final RestClient restClient;

    public CvdRiskController() {
        this.restClient = RestClient.builder()
                .baseUrl("http://127.0.0.1:8000")
                .build();
    }

    @PostMapping("/cvd")
    public Map<String, Object> predictCvd(@RequestBody Map<String, Object> patientData) {

        return restClient.post()
                .uri("/predict/cvd")
                .body(patientData)
                .retrieve()
                .body(Map.class);
    }
}