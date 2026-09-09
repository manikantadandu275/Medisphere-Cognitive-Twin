package medisphere_backend;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import medisphere_backend.model.Patient;
import medisphere_backend.repository.PatientRepository;

@RestController
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to MediSphere Healthcare Platform!";
    }

    @GetMapping("/api/patients")
    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    @PostMapping("/api/patients")
    public Patient addPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }
}