package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//CustomerAddressDao class provides the database access to all the required endpoints under customer and address controllers

@Repository
public class CustomerAddressDao {

    @PersistenceContext
    private EntityManager entityManager;

    /* establish mapping between customer and sddress*/
    public CustomerAddressEntity createCustomerAddress(CustomerAddressEntity customerAddressEntity) {
        entityManager.persist(customerAddressEntity);
        return customerAddressEntity;
    }
}
