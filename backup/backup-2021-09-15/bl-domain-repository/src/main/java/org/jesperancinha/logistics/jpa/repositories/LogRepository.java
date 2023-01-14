package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.dao.BridgeLog;
import org.springframework.data.repository.CrudRepository;

public interface LogRepository extends CrudRepository<BridgeLog, Long> {
}
