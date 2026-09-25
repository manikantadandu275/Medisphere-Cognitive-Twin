package medisphere_backend.careplan;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "careplans")
public class Careplan {

    @Id
    private String id;
    private String patientId;
    private String patientName;
    private String title;
    private String version;
    private String generatedBy;
    private String targetGoal;
    private List<String> interventions;
    private List<String> monitoringRules;
    private String baselineRisk;
    private String predictedRisk;
    private double adherenceScore;
    private String status; // PENDING_APPROVAL, APPROVED, REJECTED
    private String signedBy;
    private LocalDateTime timestamp;
    private LocalDateTime actionTime;

    public Careplan() {}

    public Careplan(String id, String patientId, String patientName, String title, String version,
                    String generatedBy, String targetGoal, List<String> interventions,
                    List<String> monitoringRules, String baselineRisk, String predictedRisk,
                    double adherenceScore, String status, String signedBy,
                    LocalDateTime timestamp, LocalDateTime actionTime) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.title = title;
        this.version = version;
        this.generatedBy = generatedBy;
        this.targetGoal = targetGoal;
        this.interventions = interventions;
        this.monitoringRules = monitoringRules;
        this.baselineRisk = baselineRisk;
        this.predictedRisk = predictedRisk;
        this.adherenceScore = adherenceScore;
        this.status = status;
        this.signedBy = signedBy;
        this.timestamp = timestamp;
        this.actionTime = actionTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getGeneratedBy() { return generatedBy; }
    public void setGeneratedBy(String generatedBy) { this.generatedBy = generatedBy; }

    public String getTargetGoal() { return targetGoal; }
    public void setTargetGoal(String targetGoal) { this.targetGoal = targetGoal; }

    public List<String> getInterventions() { return interventions; }
    public void setInterventions(List<String> interventions) { this.interventions = interventions; }

    public List<String> getMonitoringRules() { return monitoringRules; }
    public void setMonitoringRules(List<String> monitoringRules) { this.monitoringRules = monitoringRules; }

    public String getBaselineRisk() { return baselineRisk; }
    public void setBaselineRisk(String baselineRisk) { this.baselineRisk = baselineRisk; }

    public String getPredictedRisk() { return predictedRisk; }
    public void setPredictedRisk(String predictedRisk) { this.predictedRisk = predictedRisk; }

    public double getAdherenceScore() { return adherenceScore; }
    public void setAdherenceScore(double adherenceScore) { this.adherenceScore = adherenceScore; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSignedBy() { return signedBy; }
    public void setSignedBy(String signedBy) { this.signedBy = signedBy; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public LocalDateTime getActionTime() { return actionTime; }
    public void setActionTime(LocalDateTime actionTime) { this.actionTime = actionTime; }
}
