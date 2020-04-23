package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface BridgeProductRepository extends CrudRepository<Product, Long> {
}
