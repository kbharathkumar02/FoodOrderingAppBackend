package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

//OrderItemDao class provides the database access to all the required endpoints under order and item controller

@Repository
public class OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    /* To map item to order */
    public OrderItemEntity createOrderItemEntity(OrderItemEntity orderItemEntity) {
        entityManager.persist(orderItemEntity);
        return orderItemEntity;
    }

    /* Method to fetch items based on order object*/
    public List<OrderItemEntity> getItemsByOrder(OrderEntity orderEntity) {
        try {
            return entityManager.createNamedQuery("itemsByOrder", OrderItemEntity.class).setParameter("orderEntity", orderEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
