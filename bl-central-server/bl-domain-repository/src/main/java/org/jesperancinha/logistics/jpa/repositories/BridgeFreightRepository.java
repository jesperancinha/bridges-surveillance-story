package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.Freight;
import org.springframework.data.repository.CrudRepository;

public interface BridgeFreightRepository extends CrudRepository<Freight, Long> {
}
