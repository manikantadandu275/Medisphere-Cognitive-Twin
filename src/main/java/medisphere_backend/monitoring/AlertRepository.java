package medisphere_backend.monitoring;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertRepository extends MongoRepository<Alert, String> {

    List<Alert> findTop20ByOrderByTimestampDesc();

    List<Alert> findByTimestampBetweenOrderByTimestampDesc(
            LocalDateTime start,
            LocalDateTime end
    );

    long countByTimestampBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    long countByClinicalAction(String clinicalAction);
}