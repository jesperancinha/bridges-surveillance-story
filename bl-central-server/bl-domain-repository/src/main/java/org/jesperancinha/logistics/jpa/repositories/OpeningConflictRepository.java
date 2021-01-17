package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.BridgeOpeningConflict;
import org.springframework.data.repository.CrudRepository;

public interface OpeningConflictRepository extends CrudRepository<BridgeOpeningConflict, Long> {
}
