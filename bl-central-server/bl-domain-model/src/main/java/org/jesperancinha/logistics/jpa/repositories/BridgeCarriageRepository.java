package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.BridgeLog;
import org.jesperancinha.logistics.jpa.model.Carriage;
import org.springframework.data.repository.CrudRepository;

public interface BridgeCarriageRepository extends CrudRepository<Carriage, Long> {
}
