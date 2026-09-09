package medisphere_backend.digitaltwin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import medisphere_backend.model.Patient;
import medisphere_backend.repository.PatientRepository;

@RestController
public class PatientDigitalTwinController {

    private final PatientRepository patientRepository;
    private final PatientDigitalTwinRepository digitalTwinRepository;

    public PatientDigitalTwinController(
            PatientRepository patientRepository,
            PatientDigitalTwinRepository digitalTwinRepository) {
        this.patientRepository = patientRepository;
        this.digitalTwinRepository = digitalTwinRepository;
    }

    @PostMapping("/api/digital-twin/create")
    public PatientDigitalTwin createDigitalTwin() {

        Patient patient = patientRepository.findAll().get(0);

        PatientDigitalTwin twin = new PatientDigitalTwin(
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getBloodGroup()
        );

        return digitalTwinRepository.save(twin);
    }
}