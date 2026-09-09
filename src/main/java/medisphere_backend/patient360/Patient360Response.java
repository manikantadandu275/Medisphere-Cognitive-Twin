package medisphere_backend.patient360;

import medisphere_backend.digitaltwin.PatientDigitalTwin;
import medisphere_backend.wearable.WearableData;

public class Patient360Response {

    private PatientDigitalTwin digitalTwin;
    private WearableData wearableData;

    public Patient360Response() {
    }

    public Patient360Response(PatientDigitalTwin digitalTwin,
                              WearableData wearableData) {
        this.digitalTwin = digitalTwin;
        this.wearableData = wearableData;
    }

    public PatientDigitalTwin getDigitalTwin() {
        return digitalTwin;
    }

    public WearableData getWearableData() {
        return wearableData;
    }

    public void setDigitalTwin(PatientDigitalTwin digitalTwin) {
        this.digitalTwin = digitalTwin;
    }

    public void setWearableData(WearableData wearableData) {
        this.wearableData = wearableData;
    }
}