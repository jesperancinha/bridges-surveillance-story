package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.Container;
import org.springframework.data.repository.CrudRepository;

public interface BridgeContainerRepository extends CrudRepository<Container, Long> {
}
