package org.jesperancinha.logistics.mcs.repository;

import org.jesperancinha.logistics.mcs.data.MerchandiseDto;
import org.springframework.data.repository.CrudRepository;

public interface MerchandiseRepository extends CrudRepository<MerchandiseDto, Long> {
}
