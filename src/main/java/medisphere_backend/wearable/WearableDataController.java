package medisphere_backend.wearable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WearableDataController {

    private final WearableDataRepository wearableDataRepository;

    public WearableDataController(WearableDataRepository wearableDataRepository) {
        this.wearableDataRepository = wearableDataRepository;
    }

    @PostMapping("/api/wearable")
    public WearableData addWearableData(@RequestBody WearableData data) {
        return wearableDataRepository.save(data);
    }
}