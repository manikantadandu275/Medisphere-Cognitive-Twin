package medisphere_backend.monitoring;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "alerts")
public class Alert {

    @Id
    private String id;

    private String patientId;
    private String status;
    private String severity;
    private String alert;
    private String message;

    private double heartRate;
    private double spo2;
    private double temperature;

    private String aiAnalysis;
    private int aiConfidence;

    private String notification;
    private String autoAction;
    private String wearableStatus;

    // Milestone 3 clinical action fields
    private String clinicalAction;
    private LocalDateTime actionTime;

    private LocalDateTime timestamp;

    public Alert() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(double heartRate) {
        this.heartRate = heartRate;
    }

    public double getSpo2() {
        return spo2;
    }

    public void setSpo2(double spo2) {
        this.spo2 = spo2;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getAiAnalysis() {
        return aiAnalysis;
    }

    public void setAiAnalysis(String aiAnalysis) {
        this.aiAnalysis = aiAnalysis;
    }

    public int getAiConfidence() {
        return aiConfidence;
    }

    public void setAiConfidence(int aiConfidence) {
        this.aiConfidence = aiConfidence;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getAutoAction() {
        return autoAction;
    }

    public void setAutoAction(String autoAction) {
        this.autoAction = autoAction;
    }

    public String getWearableStatus() {
        return wearableStatus;
    }

    public void setWearableStatus(String wearableStatus) {
        this.wearableStatus = wearableStatus;
    }

    public String getClinicalAction() {
        return clinicalAction;
    }

    public void setClinicalAction(String clinicalAction) {
        this.clinicalAction = clinicalAction;
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}