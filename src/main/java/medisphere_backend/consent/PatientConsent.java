package medisphere_backend.consent;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patient_consent")
public class PatientConsent {

    @Id
    private String id;

    private String patientId;
    private boolean consentGiven;

    public PatientConsent() {
    }

    public PatientConsent(String patientId, boolean consentGiven) {
        this.patientId = patientId;
        this.consentGiven = consentGiven;
    }

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public boolean isConsentGiven() {
        return consentGiven;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setConsentGiven(boolean consentGiven) {
        this.consentGiven = consentGiven;
    }
}