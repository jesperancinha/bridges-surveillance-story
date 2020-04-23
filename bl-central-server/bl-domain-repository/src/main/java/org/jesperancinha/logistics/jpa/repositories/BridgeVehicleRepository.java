package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface BridgeVehicleRepository extends CrudRepository<Vehicle, Long> {
}
