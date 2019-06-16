package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

/**
 * AddressDao class provides the database access for all the endpoints in address controller
 */
@Repository
public class AddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    /* Creates address item in db based on address details provided*/
    public AddressEntity createAddress(AddressEntity addressEntity) {
        entityManager.persist(addressEntity);
        return addressEntity;
    }

    /* To fetch address based on address uuid*/
    public AddressEntity getAddressByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("addressByUUID", AddressEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /* Modify address based on the address object */
    public AddressEntity updateAddressEntity(AddressEntity addressEntity) {
        return entityManager.merge(addressEntity);
    }

    /* Delete address based on address entity object */
    public AddressEntity deleteAddressEntity(AddressEntity addressEntity) {
        entityManager.remove(addressEntity);
        return addressEntity;
    }
}
