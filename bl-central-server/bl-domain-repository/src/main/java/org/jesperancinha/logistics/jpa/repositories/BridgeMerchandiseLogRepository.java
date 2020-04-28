package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.MerchandiseLog;
import org.springframework.data.repository.CrudRepository;

public interface BridgeMerchandiseLogRepository extends CrudRepository<MerchandiseLog, Long> {
}
