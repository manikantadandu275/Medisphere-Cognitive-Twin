package medisphere_backend.config;

import medisphere_backend.careplan.Careplan;
import medisphere_backend.careplan.CareplanRepository;
import medisphere_backend.consent.PatientConsent;
import medisphere_backend.consent.PatientConsentRepository;
import medisphere_backend.digitaltwin.PatientDigitalTwin;
import medisphere_backend.digitaltwin.PatientDigitalTwinRepository;
import medisphere_backend.model.Patient;
import medisphere_backend.monitoring.Alert;
import medisphere_backend.monitoring.AlertRepository;
import medisphere_backend.repository.PatientRepository;
import medisphere_backend.wearable.WearableData;
import medisphere_backend.wearable.WearableDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final PatientDigitalTwinRepository digitalTwinRepository;
    private final WearableDataRepository wearableDataRepository;
    private final PatientConsentRepository consentRepository;
    private final AlertRepository alertRepository;
    private final CareplanRepository careplanRepository;

    public DataInitializer(PatientRepository patientRepository,
                           PatientDigitalTwinRepository digitalTwinRepository,
                           WearableDataRepository wearableDataRepository,
                           PatientConsentRepository consentRepository,
                           AlertRepository alertRepository,
                           CareplanRepository careplanRepository) {
        this.patientRepository = patientRepository;
        this.digitalTwinRepository = digitalTwinRepository;
        this.wearableDataRepository = wearableDataRepository;
        this.consentRepository = consentRepository;
        this.alertRepository = alertRepository;
        this.careplanRepository = careplanRepository;
    }

    @Override
    public void run(String... args) {
        // 1. Seed Patients if empty
        if (patientRepository.count() == 0) {
            Patient p1 = new Patient("P101", "John Doe", 58, "Male", "O+");
            Patient p2 = new Patient("P102", "Sarah M.", 42, "Female", "A+");
            patientRepository.saveAll(List.of(p1, p2));
        }

        // 2. Seed Digital Twin if empty
        if (digitalTwinRepository.count() == 0) {
            PatientDigitalTwin twin1 = new PatientDigitalTwin("P101", "John Doe", 58, "Male", "O+");
            PatientDigitalTwin twin2 = new PatientDigitalTwin("P102", "Sarah M.", 42, "Female", "A+");
            digitalTwinRepository.saveAll(List.of(twin1, twin2));
        }

        // 3. Seed Wearable Data if empty
        if (wearableDataRepository.count() == 0) {
            WearableData w1 = new WearableData("P101", 72, 98, 4320, 36.7);
            WearableData w2 = new WearableData("P102", 145, 98, 1200, 36.7);
            wearableDataRepository.saveAll(List.of(w1, w2));
        }

        // 4. Seed Patient Consent if empty
        if (consentRepository.count() == 0) {
            PatientConsent c1 = new PatientConsent("P101", true);
            PatientConsent c2 = new PatientConsent("P102", true);
            consentRepository.saveAll(List.of(c1, c2));
        }

        // 5. Seed Alerts if empty
        if (alertRepository.count() == 0) {
            Alert alt1 = new Alert();
            alt1.setPatientId("P102");
            alt1.setStatus("ALERT");
            alt1.setSeverity("HIGH");
            alt1.setAlert("HR Spike 145 BPM - Possible AFib");
            alt1.setMessage("Kafka vitals streaming flagged abnormal cardiac rhythm at rest.");
            alt1.setHeartRate(145);
            alt1.setSpo2(98);
            alt1.setTemperature(36.7);
            alt1.setAiAnalysis("Possible Atrial Fibrillation (89% confidence)");
            alt1.setAiConfidence(89);
            alt1.setNotification("Cardiologist Auto-Notified");
            alt1.setAutoAction("ECG Scheduled & Clinician Alert Triggered");
            alt1.setWearableStatus("Connected (Kafka Stream Live)");
            alt1.setClinicalAction("PENDING");
            alt1.setTimestamp(LocalDateTime.now().minusMinutes(5));

            Alert alt2 = new Alert();
            alt2.setPatientId("P101");
            alt2.setStatus("WARNING");
            alt2.setSeverity("MEDIUM");
            alt2.setAlert("Reduced SpO2 Level (92%)");
            alt2.setMessage("Patient oxygen saturation dropped below 94% during sleep cycle.");
            alt2.setHeartRate(82);
            alt2.setSpo2(92);
            alt2.setTemperature(36.8);
            alt2.setAiAnalysis("Mild nocturnal hypoxemia detected (84% confidence)");
            alt2.setAiConfidence(84);
            alt2.setNotification("On-Call Nurse Notified");
            alt2.setAutoAction("Continuous SpO2 Tracking Enabled");
            alt2.setWearableStatus("Connected (Kafka Stream Live)");
            alt2.setClinicalAction("ACKNOWLEDGED");
            alt2.setActionTime(LocalDateTime.now().minusMinutes(20));
            alt2.setTimestamp(LocalDateTime.now().minusMinutes(25));

            alertRepository.saveAll(List.of(alt1, alt2));
        }

        // 6. Seed Careplan if empty
        if (careplanRepository.count() == 0) {
            Careplan plan = new Careplan();
            plan.setId("CP-101-2026");
            plan.setPatientId("P101");
            plan.setPatientName("John Doe");
            plan.setTitle("Precision Diabetes & CVD Careplan v2.1");
            plan.setVersion("v2.1");
            plan.setGeneratedBy("MediSphere AI Clinical Guideline Engine");
            plan.setTargetGoal("Reduce HbA1c to <7.0% in 3 months; Keep BP < 130/80 mmHg");
            plan.setInterventions(List.of(
                    "Increase Metformin to 1000mg BID",
                    "Add Amlodipine 5mg QD for blood pressure optimization",
                    "Weekly blood glucose & daily blood pressure logs via wearable sync",
                    "Low-sodium Mediterranean dietary program"
            ));
            plan.setMonitoringRules(List.of(
                    "Kafka vitals streaming with real-time alert trigger on HR > 120 bpm",
                    "Continuous SpO2 tracking during sleep",
                    "Bi-weekly telehealth review"
            ));
            plan.setBaselineRisk("24.3% 10-Year CVD Risk (High Risk)");
            plan.setPredictedRisk("16.2% 10-Year CVD Risk (Reduced by 33.3%)");
            plan.setAdherenceScore(87.4);
            plan.setStatus("PENDING_APPROVAL");
            plan.setSignedBy("Pending Clinician Electronic Signature");
            plan.setTimestamp(LocalDateTime.now());
            careplanRepository.save(plan);
        }
    }
}
