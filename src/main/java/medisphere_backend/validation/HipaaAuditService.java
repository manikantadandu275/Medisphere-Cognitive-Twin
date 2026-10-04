package medisphere_backend.validation;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class HipaaAuditService {

    private final List<Map<String, String>> logs = new CopyOnWriteArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public HipaaAuditService() {
        // Initial audit events
        logAction("READ_PATIENT_TWIN", "Dr. Sarah Jenkins", "P101", "SUCCESS - PHI Access Verified");
        logAction("EVALUATE_CVD_RISK", "AI Prediction Engine", "P101", "SUCCESS - 10-Yr CVD 24.3%");
        logAction("CLINICAL_GUIDELINE_CHECK", "MediSphere Guideline Engine", "P101", "PASSED - ADA 2026 & ACC/AHA");
        logAction("DRUG_INTERACTION_SCREEN", "Clinical Decision Support", "P101", "PASSED - 0 Contraindications");
        logAction("GENERATE_CAREPLAN", "Clinical Decision Support", "P101", "SUCCESS - Precision Careplan v2.1 Generated");
    }

    public void logAction(String action, String user, String patientId, String status) {
        String timestamp = LocalDateTime.now().format(formatter);
        logs.add(0, Map.of(
                "timestamp", timestamp,
                "action", action,
                "user", user,
                "patientId", patientId,
                "status", status
        ));
    }

    public List<Map<String, String>> getLogs() {
        return new ArrayList<>(logs);
    }
}
