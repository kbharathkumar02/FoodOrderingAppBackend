package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

//CustomerDao class provides the database access to all the endpoints under customer controller

@Repository
public class CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    /* Method to create new customer from customer details provided */
    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    /* Method to fetch customer object based on customer contact number */
    public CustomerEntity getCustomerByContactNumber(String contactNumber) {
        try {
            return entityManager.createNamedQuery("customerByContactNumber", CustomerEntity.class).setParameter("contactNumber", contactNumber).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /*Method to create new cutomer authorixation from custauthentity*/
    public CustomerAuthEntity createCustomerAuth(CustomerAuthEntity customerAuthEntity) {
        entityManager.persist(customerAuthEntity);
        return customerAuthEntity;
    }

    /* Method to fetch customer object based on access-token */
    public CustomerAuthEntity getCustomerAuthByAccessToken(String accessToken) {
        try {
            return entityManager.createNamedQuery("customerAuthByAccessToken", CustomerAuthEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /* Method to update customer authorization entity */
    public CustomerAuthEntity updateCustomerAuth(CustomerAuthEntity customerAuthEntity) {
        return entityManager.merge(customerAuthEntity);
    }

    /*Method to modify customer details */
    public CustomerEntity updateCustomerEntity(CustomerEntity customerEntity) {
        return entityManager.merge(customerEntity);
    }

    /* Method to fetch customer object based on customer UUID*/
    public CustomerEntity getCustomerByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("customerByUUID", CustomerEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
