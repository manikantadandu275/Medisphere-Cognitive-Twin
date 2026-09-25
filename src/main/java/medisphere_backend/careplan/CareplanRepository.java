package medisphere_backend.careplan;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareplanRepository extends MongoRepository<Careplan, String> {
    List<Careplan> findByPatientId(String patientId);
    List<Careplan> findByPatientIdOrderByTimestampDesc(String patientId);
}
