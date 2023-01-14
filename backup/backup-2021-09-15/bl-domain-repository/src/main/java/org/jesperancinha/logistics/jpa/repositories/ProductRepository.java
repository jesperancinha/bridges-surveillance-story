package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.dao.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
