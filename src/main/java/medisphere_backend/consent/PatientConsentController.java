package medisphere_backend.consent;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientConsentController {

    private final PatientConsentRepository consentRepository;

    public PatientConsentController(PatientConsentRepository consentRepository) {
        this.consentRepository = consentRepository;
    }

    @PostMapping("/api/consent")
    public PatientConsent giveConsent(@RequestBody PatientConsent consent) {
        return consentRepository.save(consent);
    }
}