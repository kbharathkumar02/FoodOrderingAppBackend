package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * StateDao class provides the database access for all the required endpoints in address controller
 */
@Repository
public class StateDao {

    @PersistenceContext
    private EntityManager entityManager;

    /* fetch state based on state uuid*/
    public StateEntity getStateByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("stateByUUID", StateEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /* method to fetch list of all states */
    public List<StateEntity> getAllStates() {
        try {
            return entityManager.createNamedQuery("allStates", StateEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
