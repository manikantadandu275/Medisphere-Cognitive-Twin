package medisphere_backend.wearable;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WearableDataRepository
        extends MongoRepository<WearableData, String> {
}