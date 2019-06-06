package com.jesperancinha.bridgelogistics.jpa.repositories;

import com.jesperancinha.bridgelogistics.jpa.model.Bridge;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BridgeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Bridge> findAllBridges() {
        return entityManager.createNamedQuery("Bridge.findAll", Bridge.class).getResultList();
    }

}
