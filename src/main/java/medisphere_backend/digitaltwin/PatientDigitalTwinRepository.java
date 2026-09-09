package medisphere_backend.digitaltwin;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientDigitalTwinRepository
        extends MongoRepository<PatientDigitalTwin, String> {
}