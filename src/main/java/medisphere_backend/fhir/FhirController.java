package medisphere_backend.fhir;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import medisphere_backend.model.Patient;
import medisphere_backend.repository.PatientRepository;

@RestController
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://localhost:50903"
})
public class FhirController {

    private final PatientRepository patientRepository;

    public FhirController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/fhir/Patient")
    public FhirPatient getFhirPatient() {

        Patient patient = patientRepository.findAll().stream().findFirst().orElseGet(() -> {
            Patient defaultPatient = new Patient("P101", "John Doe", 58, "Male", "O+");
            return patientRepository.save(defaultPatient);
        });

        return new FhirPatient(
                patient.getId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender()
        );
    }
}