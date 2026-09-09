package medisphere_backend.fhir;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import medisphere_backend.model.Patient;
import medisphere_backend.repository.PatientRepository;

@RestController
public class FhirController {

    private final PatientRepository patientRepository;

    public FhirController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/fhir/Patient")
    public FhirPatient getFhirPatient() {

        Patient patient = patientRepository.findAll().get(0);

        return new FhirPatient(
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender()
        );
    }
}