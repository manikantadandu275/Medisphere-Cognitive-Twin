package medisphere_backend.consent;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientConsentRepository
        extends MongoRepository<PatientConsent, String> {
}