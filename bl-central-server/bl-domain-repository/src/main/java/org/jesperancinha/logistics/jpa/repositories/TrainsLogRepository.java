package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.TrainLog;
import org.springframework.data.repository.CrudRepository;

public interface TrainsLogRepository extends CrudRepository<TrainLog, Long> {
}
