package org.jesperancinha.logistics.mcs.repository;

import org.jesperancinha.logistics.mcs.data.Merchandise;
import org.springframework.data.repository.CrudRepository;

public interface MerchandiseRepository extends CrudRepository<Merchandise, Long> {
}
