package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.Train;
import org.springframework.data.repository.CrudRepository;

public interface BridgeTrainRepository extends CrudRepository<Train, Long> {
}
