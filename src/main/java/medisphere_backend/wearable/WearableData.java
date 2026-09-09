package medisphere_backend.wearable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wearable_data")
public class WearableData {

    @Id
    private String id;

    private String patientId;
    private int heartRate;
    private int spo2;
    private int steps;
    private double temperature;

    public WearableData() {
    }

    public WearableData(String patientId, int heartRate, int spo2,
                        int steps, double temperature) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.spo2 = spo2;
        this.steps = steps;
        this.temperature = temperature;
    }

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getSpo2() {
        return spo2;
    }

    public int getSteps() {
        return steps;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public void setSpo2(int spo2) {
        this.spo2 = spo2;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}