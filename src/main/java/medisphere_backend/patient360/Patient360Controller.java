package medisphere_backend.patient360;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import medisphere_backend.digitaltwin.PatientDigitalTwin;
import medisphere_backend.digitaltwin.PatientDigitalTwinRepository;
import medisphere_backend.wearable.WearableData;
import medisphere_backend.wearable.WearableDataRepository;

@RestController
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://localhost:50903"
})
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

        PatientDigitalTwin twin = digitalTwinRepository.findAll().stream().findFirst().orElseGet(() -> {
            PatientDigitalTwin defaultTwin = new PatientDigitalTwin("P101", "John Doe", 58, "Male", "O+");
            return digitalTwinRepository.save(defaultTwin);
        });

        WearableData wearable = wearableDataRepository.findAll().stream().findFirst().orElseGet(() -> {
            WearableData defaultWearable = new WearableData("P101", 145, 98, 4320, 36.7);
            return wearableDataRepository.save(defaultWearable);
        });

        return new Patient360Response(twin, wearable);
    }
}