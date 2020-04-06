package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.Bridge;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BridgeRepository {

    @PersistenceContext(unitName = "bridge-logistics-pu")
    private EntityManager entityManager;

    public List<Bridge> findAllBridges() {
        return entityManager.createNamedQuery("Bridge.findAll", Bridge.class).getResultList();
    }

}
