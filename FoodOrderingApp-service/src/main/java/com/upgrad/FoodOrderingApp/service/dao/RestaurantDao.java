package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

//RestaurantDao class provides the database access for all the endpoints under restaurant controller

@Repository
public class RestaurantDao {

    @PersistenceContext
    private EntityManager entityManager;

    /* To fetch all restaurants and return a list */

    public List<RestaurantEntity> restaurantsByRating() {
        try {
            return entityManager.createNamedQuery("getAllRestaurantsByRating", RestaurantEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /* To return restaurant entity object based on uuid */

    public RestaurantEntity getRestaurantByUUID(String uuid) {
        try {
            return entityManager.createNamedQuery("restaurantByUUID", RestaurantEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /* To update restaurant details and returns restaurant entity */

    public RestaurantEntity updateRestaurantEntity(RestaurantEntity restaurantEntity) {
        return entityManager.merge(restaurantEntity);
    }
}
