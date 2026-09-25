package medisphere_backend.careplan;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/careplan")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://localhost:50903"
})
public class CareplanController {

    private final CareplanRepository careplanRepository;

    public CareplanController(CareplanRepository careplanRepository) {
        this.careplanRepository = careplanRepository;
    }

    @GetMapping("/patient/{patientId}")
    public List<Careplan> getCareplansByPatient(@PathVariable String patientId) {
        List<Careplan> list = careplanRepository.findByPatientIdOrderByTimestampDesc(patientId);
        if (list.isEmpty()) {
            Careplan sample = createSampleCareplan(patientId);
            careplanRepository.save(sample);
            return List.of(sample);
        }
        return list;
    }

    @PostMapping("/generate/{patientId}")
    public Careplan generateCareplan(@PathVariable String patientId) {
        Careplan plan = createSampleCareplan(patientId);
        plan.setId(UUID.randomUUID().toString());
        plan.setTimestamp(LocalDateTime.now());
        return careplanRepository.save(plan);
    }

    @PostMapping("/{id}/approve")
    public Careplan approveCareplan(@PathVariable String id, @RequestParam(defaultValue = "Dr. Sarah Jenkins (NPI 1849204812)") String providerName) {
        Optional<Careplan> optional = careplanRepository.findById(id);
        if (optional.isPresent()) {
            Careplan plan = optional.get();
            plan.setStatus("APPROVED");
            plan.setSignedBy(providerName);
            plan.setActionTime(LocalDateTime.now());
            return careplanRepository.save(plan);
        }
        throw new RuntimeException("Careplan not found with id: " + id);
    }

    @GetMapping("/all")
    public List<Careplan> getAllCareplans() {
        return careplanRepository.findAll();
    }

    private Careplan createSampleCareplan(String patientId) {
        Careplan plan = new Careplan();
        plan.setId("CP-" + UUID.randomUUID().toString().substring(0, 8));
        plan.setPatientId(patientId);
        plan.setPatientName("John Doe");
        plan.setTitle("Precision Diabetes & CVD Careplan v2.1");
        plan.setVersion("v2.1");
        plan.setGeneratedBy("MediSphere AI Clinical Guideline Engine");
        plan.setTargetGoal("Reduce HbA1c to <7.0% in 3 months; Keep BP < 130/80 mmHg");
        plan.setInterventions(List.of(
                "Increase Metformin to 1000mg BID",
                "Add Amlodipine 5mg QD for blood pressure optimization",
                "Weekly blood glucose & daily blood pressure logs via wearable sync"
        ));
        plan.setMonitoringRules(List.of(
                "Kafka vitals streaming with real-time alert trigger on HR > 120 bpm",
                "Bi-weekly telehealth review"
        ));
        plan.setBaselineRisk("24.3% 10-Year CVD Risk (High Risk)");
        plan.setPredictedRisk("16.2% 10-Year CVD Risk (Reduced by 33.3%)");
        plan.setAdherenceScore(87.4);
        plan.setStatus("PENDING_APPROVAL");
        plan.setSignedBy("Pending Provider Electronic Signature");
        plan.setTimestamp(LocalDateTime.now());
        return plan;
    }
}
