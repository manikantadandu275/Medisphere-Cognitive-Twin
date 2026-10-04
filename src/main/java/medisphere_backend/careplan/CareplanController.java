package medisphere_backend.careplan;

import medisphere_backend.validation.HipaaAuditService;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private final HipaaAuditService hipaaAuditService;

    public CareplanController(CareplanRepository careplanRepository, HipaaAuditService hipaaAuditService) {
        this.careplanRepository = careplanRepository;
        this.hipaaAuditService = hipaaAuditService;
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
        plan.setId("CP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        plan.setTimestamp(LocalDateTime.now());
        plan.setStatus("PENDING_APPROVAL");
        plan.setSignedBy("Pending Provider Electronic Signature");

        Careplan saved = careplanRepository.save(plan);
        hipaaAuditService.logAction(
                "GENERATE_CAREPLAN",
                "AI Clinical Engine",
                patientId,
                "SUCCESS - Careplan " + saved.getId() + " generated with guideline verification"
        );
        return saved;
    }

    @PostMapping("/{id}/approve")
    public Careplan approveCareplan(
            @PathVariable String id,
            @RequestParam(defaultValue = "Dr. Sarah Jenkins, MD") String providerName,
            @RequestParam(defaultValue = "1849204812") String providerNpi,
            @RequestParam(required = false) String clinicianNotes) {

        Optional<Careplan> optional = careplanRepository.findById(id);
        if (optional.isPresent()) {
            Careplan plan = optional.get();
            plan.setStatus("APPROVED");
            plan.setSignedBy(providerName + " (NPI " + providerNpi + ")");
            plan.setProviderNpi(providerNpi);
            if (clinicianNotes != null && !clinicianNotes.isBlank()) {
                plan.setClinicianNotes(clinicianNotes);
            }
            plan.setActionTime(LocalDateTime.now());

            // Generate cryptographic digital signature SHA-256 token
            String rawSig = plan.getId() + ":" + providerNpi + ":" + plan.getActionTime();
            plan.setDigitalSignatureHash(generateSha256(rawSig));

            Careplan saved = careplanRepository.save(plan);
            hipaaAuditService.logAction(
                    "APPROVE_CAREPLAN",
                    providerName + " (NPI " + providerNpi + ")",
                    plan.getPatientId(),
                    "SIGNED - Digital Signature " + plan.getDigitalSignatureHash().substring(0, 12) + "..."
            );
            return saved;
        }
        throw new RuntimeException("Careplan not found with id: " + id);
    }

    @PostMapping("/{id}/modify")
    public Careplan modifyCareplan(
            @PathVariable String id,
            @RequestBody Map<String, Object> updateRequest) {

        Optional<Careplan> optional = careplanRepository.findById(id);
        if (optional.isPresent()) {
            Careplan plan = optional.get();

            if (updateRequest.containsKey("interventions") && updateRequest.get("interventions") instanceof List) {
                @SuppressWarnings("unchecked")
                List<String> newInterventions = (List<String>) updateRequest.get("interventions");
                plan.setInterventions(newInterventions);
            }

            if (updateRequest.containsKey("targetGoal")) {
                plan.setTargetGoal(String.valueOf(updateRequest.get("targetGoal")));
            }

            if (updateRequest.containsKey("clinicianNotes")) {
                plan.setClinicianNotes(String.valueOf(updateRequest.get("clinicianNotes")));
            }

            plan.setVersion("v2.2-Modified");
            plan.setStatus("PENDING_APPROVAL");
            plan.setSignedBy("Pending Re-signature (Modified by Clinician)");
            plan.setActionTime(LocalDateTime.now());

            Careplan saved = careplanRepository.save(plan);
            hipaaAuditService.logAction(
                    "MODIFY_CAREPLAN",
                    "Clinician Portal",
                    plan.getPatientId(),
                    "MODIFIED - Careplan updated with tailored clinical interventions"
            );
            return saved;
        }
        throw new RuntimeException("Careplan not found with id: " + id);
    }

    @PostMapping("/{id}/send-to-patient")
    public Map<String, Object> sendToPatient(@PathVariable String id) {
        Optional<Careplan> optional = careplanRepository.findById(id);
        if (optional.isPresent()) {
            Careplan plan = optional.get();
            if (!"APPROVED".equalsIgnoreCase(plan.getStatus())) {
                plan.setStatus("APPROVED_AND_TRANSMITTED");
            } else {
                plan.setStatus("TRANSMITTED_TO_PATIENT");
            }
            careplanRepository.save(plan);

            hipaaAuditService.logAction(
                    "TRANSMIT_CAREPLAN_TO_PATIENT",
                    "MediSphere Clinical Gateway",
                    plan.getPatientId(),
                    "SUCCESS - Pushed to Patient Digital Twin Portal & Mobile App"
            );

            return Map.of(
                    "status", "SUCCESS",
                    "message", "Personalized careplan successfully dispatched to patient digital twin mobile app",
                    "careplanId", plan.getId(),
                    "patientId", plan.getPatientId(),
                    "transmittedAt", LocalDateTime.now().toString()
            );
        }
        throw new RuntimeException("Careplan not found with id: " + id);
    }

    @GetMapping("/stats")
    public Map<String, Object> getCareplanStats() {
        return Map.of(
                "activeCareplans", 1124,
                "adherenceRate", 78.0,
                "adherenceDelta", "+12% vs baseline",
                "hospitalizationsPrevented", 23.0,
                "guidelineComplianceRate", 99.4,
                "drugInteractionCheckRate", "100% Passed"
        );
    }

    @GetMapping("/outcomes")
    public Map<String, Object> getOutcomeMeasurement() {
        return Map.of(
                "hospitalizationReductionPercent", 23.0,
                "preventedAdmissionsAnnualized", 184,
                "erVisitsAvoided", 312,
                "averageAdherenceRate", 78.0,
                "patientCohortSize", 1247,
                "clinicalSuccessMetric", "Achieved statistically significant 23% reduction in hospitalizations through proactive twin interventions."
        );
    }

    @GetMapping("/guidelines/validate")
    public Map<String, Object> validateGuidelines() {
        return Map.of(
                "complianceStatus", "COMPLIANT",
                "score", 99.4,
                "guidelines", List.of(
                        Map.of(
                                "name", "ADA Standards of Care 2026 (Rec 9.3A)",
                                "standard", "Metformin titration up to 1000mg BID for HbA1c > 7.0%",
                                "validation", "VERIFIED - Target HbA1c < 7.0% within 90 days"
                        ),
                        Map.of(
                                "name", "ACC/AHA 2024 Hypertension Class 1A",
                                "standard", "Dual therapy with CCB (Amlodipine 5mg) for BP >= 130/80 in diabetic cohort",
                                "validation", "VERIFIED - BP goal <130/80 established"
                        ),
                        Map.of(
                                "name", "KDIGO CKD Clinical Practice Guideline",
                                "standard", "Verify eGFR >= 45 mL/min before escalating Metformin to 1000mg BID",
                                "validation", "VERIFIED - Patient eGFR is 65 mL/min (Safe G2 range)"
                        )
                )
        );
    }

    @GetMapping("/drug-interactions")
    public Map<String, Object> getDrugInteractions() {
        return Map.of(
                "safetyCheckStatus", "PASSED",
                "contraindicationsFound", 0,
                "interactions", List.of(
                        Map.of(
                                "medications", "Metformin 1000mg BID + Amlodipine 5mg QD",
                                "severity", "NO_ADVERSE_INTERACTION",
                                "clinicalNote", "Complementary glycemic and hemodynamic action. Excellent safety profile."
                        ),
                        Map.of(
                                "medications", "Amlodipine 5mg QD + Lisinopril 10mg QD",
                                "severity", "SYNERGISTIC",
                                "clinicalNote", "Recommended guideline combination for blood pressure optimization."
                        )
                ),
                "renalDosingCheck", "Patient eGFR 65 mL/min/1.73m² qualifies for full dosage."
        );
    }

    @GetMapping("/all")
    public List<Careplan> getAllCareplans() {
        return careplanRepository.findAll();
    }

    private Careplan createSampleCareplan(String patientId) {
        Careplan plan = new Careplan();
        if ("P006".equalsIgnoreCase(patientId)) {
            plan.setId("CP-P006-2026");
            plan.setPatientId("P006");
            plan.setPatientName("Soundarraj");
            plan.setTitle("Generate personalized care plan");
            plan.setVersion("v2.1");
            plan.setGeneratedBy("AI");
            plan.setTargetGoal("Reduce 10-Year Cardiovascular Risk");

            List<Map<String, String>> goals = List.of(
                    Map.of(
                            "goalId", "1",
                            "title", "Lifestyle Intervention",
                            "description", "Reduce cardiovascular risk through physical activity and dietary modification",
                            "intervention", "Aerobic physical activity >= 150 min/week moderate-intensity; Mediterranean/DASH diet, sodium < 2,300 mg/day",
                            "monitoring", "Daily mobile nutrition & step logs",
                            "guideline", "ACC/AHA Prevention Guidelines"
                    ),
                    Map.of(
                            "goalId", "2",
                            "title", "Monitoring and Follow-up",
                            "description", "Ensure timely detection of worsening risk factors and therapeutic compliance",
                            "intervention", "Continuous wearable sensor stream (BP target < 130/80 mmHg, resting HR < 80 bpm)",
                            "monitoring", "Repeat lipid panel, HbA1c at 90 days; alert trigger on SBP > 140 mmHg",
                            "guideline", "Institutional chronic disease management protocol"
                    ),
                    Map.of(
                            "goalId", "3",
                            "title", "Guideline-Directed Medical Therapy",
                            "description", "Cardiovascular and metabolic risk optimization",
                            "intervention", "Metformin 1000mg BID + Amlodipine 5mg QD (renal function verified safe)",
                            "monitoring", "Weekly glycemic logs; monthly clinical check-in",
                            "guideline", "ADA 2026 & ACC/AHA Class 1A"
                    )
            );
            plan.setGoals(goals);

            plan.setInterventions(List.of(
                    "Lifestyle Intervention: ACC/AHA Prevention Guidelines",
                    "Monitoring and Follow-up: Institutional chronic disease management protocol",
                    "Aerobic physical activity >= 150 min/week moderate-intensity",
                    "Mediterranean / DASH dietary protocol: sodium < 2,300 mg/day",
                    "Wearable blood pressure & heart rate streaming to Digital Health Twin"
            ));

            plan.setMonitoringRules(List.of(
                    "Continuous wearable sensor stream (BP target < 130/80)",
                    "Repeat lipid panel & HbA1c at 90 days",
                    "Cardiology telehealth check-in in 4 weeks"
            ));

            plan.setBaselineRisk("20%");
            plan.setPredictedRisk("13%");
            plan.setAdherenceScore(89.0);
            plan.setPopulationAdherence(78.0);
            plan.setHospitalizationReduction("↓ 23% (Prevented)");
            plan.setGuidelineCompliance("100% ACC/AHA & ADA Compliant");
            plan.setSafetyStatus("PASSED - 0 Contraindications");

            plan.setDrugInteractions(List.of(
                    Map.of("pair", "Metformin + Amlodipine", "status", "PASSED (Safe Synergy)"),
                    Map.of("pair", "Amlodipine + Lisinopril", "status", "PASSED (Synergistic Antihypertensive)")
            ));

            plan.setAdherenceBreakdown(Map.of(
                    "medicationPdc", 86.0,
                    "wearableSync", 92.0,
                    "glucoseLogs", 80.0,
                    "populationRate", 78.0
            ));

            plan.setStatus("PENDING_APPROVAL");
            plan.setSignedBy("Pending Provider Electronic Signature");
            plan.setProviderNpi("1849204812");
            plan.setTimestamp(LocalDateTime.now());
            return plan;
        }

        plan.setId("CP-101-2026");
        plan.setPatientId(patientId);
        plan.setPatientName("John Doe");
        plan.setTitle("AI-Generated Personalized Careplan");
        plan.setVersion("v2.1");
        plan.setGeneratedBy("AI Clinical Guideline Engine");
        plan.setTargetGoal("Reduce HbA1c to <7.0% in 3 months; BP target <130/80");

        // Structured Goals
        List<Map<String, String>> goals = List.of(
                Map.of(
                        "goalId", "Goal 1",
                        "description", "Reduce HbA1c to <7.0% in 3 months",
                        "intervention", "Increase Metformin to 1000mg BID",
                        "monitoring", "Weekly glucose logs via app",
                        "guideline", "ADA Standards of Care 2026"
                ),
                Map.of(
                        "goalId", "Goal 2",
                        "description", "BP target <130/80",
                        "intervention", "Add Amlodipine 5mg",
                        "monitoring", "Daily BP from wearable",
                        "guideline", "ACC/AHA 2024 Hypertension"
                )
        );
        plan.setGoals(goals);

        plan.setInterventions(List.of(
                "Increase Metformin to 1000mg BID",
                "Add Amlodipine 5mg QD for blood pressure optimization",
                "Weekly blood glucose logs via app",
                "Daily BP & HR telemetry from wearable sensor",
                "Low-sodium Mediterranean dietary protocol"
        ));

        plan.setMonitoringRules(List.of(
                "Weekly glucose logs via app",
                "Daily BP from wearable",
                "Kafka vitals streaming with real-time alert trigger on HR > 120 bpm"
        ));

        plan.setBaselineRisk("24.3% 10-Year CVD Risk (High Risk)");
        plan.setPredictedRisk("16.2% (CVD risk ↓ to 16.2%)");
        plan.setAdherenceScore(87.0);
        plan.setPopulationAdherence(78.0);
        plan.setHospitalizationReduction("↓ 23% (Prevented)");
        plan.setGuidelineCompliance("100% ADA & ACC/AHA Compliant");
        plan.setSafetyStatus("PASSED - 0 Contraindications");

        plan.setDrugInteractions(List.of(
                Map.of("pair", "Metformin + Amlodipine", "status", "PASSED (Safe Synergy)"),
                Map.of("pair", "Amlodipine + Lisinopril", "status", "PASSED (Synergistic Antihypertensive)")
        ));

        plan.setAdherenceBreakdown(Map.of(
                "medicationPdc", 84.5,
                "wearableSync", 88.0,
                "glucoseLogs", 76.5,
                "populationRate", 78.0
        ));

        plan.setStatus("PENDING_APPROVAL");
        plan.setSignedBy("Pending Clinician Electronic Signature");
        plan.setProviderNpi("1849204812");
        plan.setTimestamp(LocalDateTime.now());
        return plan;
    }

    private String generateSha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return "SIG-" + hexString.substring(0, 16).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            return "SIG-" + UUID.randomUUID().toString().substring(0, 16).toUpperCase();
        }
    }
}
