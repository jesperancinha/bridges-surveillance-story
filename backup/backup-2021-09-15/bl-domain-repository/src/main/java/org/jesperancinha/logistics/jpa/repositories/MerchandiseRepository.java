package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.dao.Merchandise;
import org.springframework.data.repository.CrudRepository;

public interface MerchandiseRepository extends CrudRepository<Merchandise, Long> {
}
