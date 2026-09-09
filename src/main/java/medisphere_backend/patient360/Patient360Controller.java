package medisphere_backend.patient360;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import medisphere_backend.digitaltwin.PatientDigitalTwin;
import medisphere_backend.digitaltwin.PatientDigitalTwinRepository;
import medisphere_backend.wearable.WearableData;
import medisphere_backend.wearable.WearableDataRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class Patient360Controller {

    private final PatientDigitalTwinRepository digitalTwinRepository;
    private final WearableDataRepository wearableDataRepository;

    public Patient360Controller(
            PatientDigitalTwinRepository digitalTwinRepository,
            WearableDataRepository wearableDataRepository) {

        this.digitalTwinRepository = digitalTwinRepository;
        this.wearableDataRepository = wearableDataRepository;
    }

    @GetMapping("/api/patient360")
    public Patient360Response getPatient360() {

        PatientDigitalTwin twin =
                digitalTwinRepository.findAll().get(0);

        WearableData wearable =
                wearableDataRepository.findAll().get(0);

        return new Patient360Response(twin, wearable);
    }
}