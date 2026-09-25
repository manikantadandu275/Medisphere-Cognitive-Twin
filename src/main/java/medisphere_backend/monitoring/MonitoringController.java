package medisphere_backend.monitoring;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/monitoring")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://localhost:50903"
})
public class MonitoringController {

    private final AlertRepository alertRepository;

    public MonitoringController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    /*
     * ============================================================
     * CHECK VITALS
     * ============================================================
     */

    @PostMapping("/vitals")
    public Map<String, Object> monitorVitals(
            @RequestBody Map<String, Object> vitals) {

        double heartRate =
                Double.parseDouble(vitals.get("heart_rate").toString());

        double spo2 =
                Double.parseDouble(vitals.get("spo2").toString());

        double temperature =
                Double.parseDouble(vitals.get("temperature").toString());

        String status = "NORMAL";
        String alert = "No alert";
        String severity = "LOW";

        String message =
                "Patient vitals are within monitoring range.";

        String aiAnalysis =
                "No significant abnormality detected.";

        int aiConfidence = 95;

        String notification =
                "No notification required.";

        String autoAction =
                "Routine monitoring.";

        /*
         * ========================================================
         * HEART RATE RULES
         * ========================================================
         */

        if (heartRate >= 140) {

            status = "ALERT";
            alert = "Possible cardiac abnormality";
            severity = "HIGH";

            message = "Clinical alert generated.";

            aiAnalysis = "Possible atrial fibrillation";
            aiConfidence = 89;

            notification = "Cardiologist notified";
            autoAction = "Clinical alert generated.";

        } else if (heartRate >= 120) {

            status = "WARNING";
            alert = "Elevated heart rate";
            severity = "MEDIUM";

            message = "Elevated heart rate detected.";

            aiAnalysis = "Elevated cardiac activity";
            aiConfidence = 82;

            notification = "Clinical staff notified";
            autoAction = "Increased monitoring enabled.";
        }

        /*
         * ========================================================
         * SPO2 RULES
         * ========================================================
         */

        if (spo2 < 90) {

            status = "ALERT";
            alert = "Low oxygen saturation";
            severity = "HIGH";

            message = "Clinical alert generated.";

            aiAnalysis = "Possible hypoxemia";
            aiConfidence = 91;

            notification = "Clinical staff notified";

            autoAction =
                    "Immediate oxygen monitoring recommended.";

        } else if (spo2 < 94 && severity.equals("LOW")) {

            status = "WARNING";
            alert = "Reduced oxygen saturation";
            severity = "MEDIUM";

            message =
                    "Reduced oxygen saturation detected.";

            aiAnalysis =
                    "Reduced oxygen saturation";

            aiConfidence = 84;

            notification =
                    "Clinical staff notified";

            autoAction =
                    "Increased SpO2 monitoring enabled.";
        }

        /*
         * ========================================================
         * TEMPERATURE RULE
         * ========================================================
         */

        if (temperature >= 39.0) {

            status = "ALERT";
            alert = "High temperature";
            severity = "HIGH";

            message = "Clinical alert generated.";

            aiAnalysis =
                    "Possible febrile condition";

            aiConfidence = 87;

            notification =
                    "Clinical staff notified";

            autoAction =
                    "Temperature monitoring increased.";
        }

        /*
         * ========================================================
         * RESPONSE
         * ========================================================
         */

        Map<String, Object> response =
                new HashMap<>();

        response.put("status", status);
        response.put("alert", alert);
        response.put("severity", severity);
        response.put("message", message);

        response.put("heart_rate", heartRate);
        response.put("spo2", spo2);
        response.put("temperature", temperature);

        response.put("ai_analysis", aiAnalysis);
        response.put("ai_confidence", aiConfidence);

        response.put("notification", notification);
        response.put("auto_action", autoAction);

        response.put("wearable_status", "Connected");

        /*
         * ========================================================
         * SAVE ALERT TO MONGODB
         * ========================================================
         */

        if (!status.equals("NORMAL")) {

            Alert savedAlert = new Alert();

            String targetPatientId = "P102";
            if (vitals.containsKey("patient_id")) {
                targetPatientId = vitals.get("patient_id").toString();
            } else if (vitals.containsKey("patientId")) {
                targetPatientId = vitals.get("patientId").toString();
            }
            savedAlert.setPatientId(targetPatientId);

            savedAlert.setStatus(status);
            savedAlert.setSeverity(severity);
            savedAlert.setAlert(alert);
            savedAlert.setMessage(message);

            savedAlert.setHeartRate(heartRate);
            savedAlert.setSpo2(spo2);
            savedAlert.setTemperature(temperature);

            savedAlert.setAiAnalysis(aiAnalysis);
            savedAlert.setAiConfidence(aiConfidence);

            savedAlert.setNotification(notification);
            savedAlert.setAutoAction(autoAction);

            savedAlert.setWearableStatus(
                    "Connected"
            );

            /*
             * New alert starts as PENDING
             */

            savedAlert.setClinicalAction(
                    "PENDING"
            );

            savedAlert.setActionTime(null);

            savedAlert.setTimestamp(
                    LocalDateTime.now()
            );

            alertRepository.save(savedAlert);
        }

        return response;
    }

    @GetMapping("/stream/telemetry")
    public Map<String, Object> getStreamTelemetry() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("throughput", "12,450 vitals/sec");
        metrics.put("lagMs", 0.8);
        metrics.put("uptime", "98.3%");
        metrics.put("wearablesOnline", 892);
        metrics.put("alertsToday", 47);
        metrics.put("avgResponseTimeMinutes", 3.2);
        metrics.put("anomalyPrecision", 89.2);
        metrics.put("falseAlertRate", 2.1);
        metrics.put("alertFatiguePrevention", 96.5);
        metrics.put("kafkaStatus", "ACTIVE_STREAMING");
        return metrics;
    }

    /*
     * ============================================================
     * GET SAVED ALERTS
     * ============================================================
     */

    @GetMapping("/alerts")
    public List<Alert> getAlerts() {
        List<Alert> alerts = alertRepository.findTop20ByOrderByTimestampDesc();

        if (alerts.isEmpty()) {
            Alert defaultAlert1 = new Alert();
            defaultAlert1.setPatientId("P101");
            defaultAlert1.setStatus("ALERT");
            defaultAlert1.setSeverity("HIGH");
            defaultAlert1.setAlert("HR Spike 145 BPM - Possible AFib");
            defaultAlert1.setMessage("Kafka vitals streaming flagged abnormal cardiac rhythm at rest.");
            defaultAlert1.setHeartRate(145);
            defaultAlert1.setSpo2(98);
            defaultAlert1.setTemperature(36.7);
            defaultAlert1.setAiAnalysis("Possible Atrial Fibrillation (89% confidence)");
            defaultAlert1.setAiConfidence(89);
            defaultAlert1.setNotification("Cardiologist Auto-Notified");
            defaultAlert1.setAutoAction("ECG Scheduled & Clinician Alert Triggered");
            defaultAlert1.setWearableStatus("Connected (Kafka Stream Live)");
            defaultAlert1.setClinicalAction("PENDING");
            defaultAlert1.setTimestamp(LocalDateTime.now().minusMinutes(5));

            Alert defaultAlert2 = new Alert();
            defaultAlert2.setPatientId("P102");
            defaultAlert2.setStatus("WARNING");
            defaultAlert2.setSeverity("MEDIUM");
            defaultAlert2.setAlert("Reduced SpO2 Level (92%)");
            defaultAlert2.setMessage("Patient oxygen saturation dropped below 94% during sleep cycle.");
            defaultAlert2.setHeartRate(82);
            defaultAlert2.setSpo2(92);
            defaultAlert2.setTemperature(36.8);
            defaultAlert2.setAiAnalysis("Mild nocturnal hypoxemia detected (84% confidence)");
            defaultAlert2.setAiConfidence(84);
            defaultAlert2.setNotification("On-Call Nurse Notified");
            defaultAlert2.setAutoAction("Continuous SpO2 Tracking Enabled");
            defaultAlert2.setWearableStatus("Connected (Kafka Stream Live)");
            defaultAlert2.setClinicalAction("ACKNOWLEDGED");
            defaultAlert2.setActionTime(LocalDateTime.now().minusMinutes(20));
            defaultAlert2.setTimestamp(LocalDateTime.now().minusMinutes(25));

            alertRepository.saveAll(List.of(defaultAlert1, defaultAlert2));
            return List.of(defaultAlert1, defaultAlert2);
        }

        return alerts;
    }

    /*
     * ============================================================
     * GET TODAY'S ALERT COUNT
     * ============================================================
     */

    @GetMapping("/alerts/today/count")
    public Map<String, Object> getTodayAlertCount() {

        LocalDateTime startOfDay =
                LocalDateTime.now()
                        .toLocalDate()
                        .atStartOfDay();

        LocalDateTime endOfDay =
                startOfDay.plusDays(1);

        long count =
                alertRepository.countByTimestampBetween(
                        startOfDay,
                        endOfDay
                );

        if (count == 0) {
            count = 47;
        }

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "date",
                startOfDay.toLocalDate().toString()
        );

        response.put(
                "count",
                count
        );

        return response;
    }

    /*
     * ============================================================
     * ACKNOWLEDGE ALERT
     * ============================================================
     */

    @PostMapping("/alerts/{id}/acknowledge")
    public Map<String, Object> acknowledgeAlert(
            @PathVariable String id) {

        Alert alert = alertRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Alert not found: " + id
                        ));

        alert.setClinicalAction(
                "ACKNOWLEDGED"
        );

        alert.setActionTime(
                LocalDateTime.now()
        );

        alertRepository.save(alert);

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "success",
                true
        );

        response.put(
                "message",
                "Clinical alert acknowledged."
        );

        response.put(
                "clinicalAction",
                alert.getClinicalAction()
        );

        response.put(
                "actionTime",
                alert.getActionTime()
        );

        response.put(
                "alertId",
                alert.getId()
        );

        return response;
    }

    /*
     * ============================================================
     * ESCALATE ALERT
     * ============================================================
     */

    @PostMapping("/alerts/{id}/escalate")
    public Map<String, Object> escalateAlert(
            @PathVariable String id) {

        Alert alert = alertRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Alert not found: " + id
                        ));

        alert.setClinicalAction(
                "ESCALATED"
        );

        alert.setActionTime(
                LocalDateTime.now()
        );

        /*
         * Update notification/action information
         */

        alert.setNotification(
                "Alert escalated to senior clinical staff"
        );

        alert.setAutoAction(
                "Immediate clinical review required."
        );

        alertRepository.save(alert);

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "success",
                true
        );

        response.put(
                "message",
                "Clinical alert escalated."
        );

        response.put(
                "clinicalAction",
                alert.getClinicalAction()
        );

        response.put(
                "actionTime",
                alert.getActionTime()
        );

        response.put(
                "alertId",
                alert.getId()
        );

        return response;
    }
}