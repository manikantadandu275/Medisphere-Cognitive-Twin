package medisphere_backend.digitaltwin;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDigitalTwinRepository
        extends MongoRepository<PatientDigitalTwin, String> {
}