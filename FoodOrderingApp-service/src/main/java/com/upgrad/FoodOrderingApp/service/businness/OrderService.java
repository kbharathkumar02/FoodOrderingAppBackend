package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private CustomerDao customerDao;

    /* Method to find coupon by coupon name else would throw coupon not found exception*/
    public CouponEntity getCouponByCouponName(String couponName) throws CouponNotFoundException {

        if (couponName.equals("")) {
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        }

        CouponEntity couponEntity = couponDao.getCouponByCouponName(couponName);

        if (couponEntity == null) {
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }

        return couponEntity;
    }

    /* to fetch coupon by coupon uuid else would throw coupon not found exception */
    public CouponEntity getCouponByCouponId(String uuid) throws CouponNotFoundException {
        CouponEntity couponEntity = couponDao.getCouponByCouponUUID(uuid);

        if (couponEntity == null) {
            throw new CouponNotFoundException("CPF-001", "No coupon by this id");
        }

        return couponEntity;
    }

    /* To save the order */
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return orderDao.createOrder(orderEntity);
    }

    /*Method to create order to item mapping */
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity) {
        return orderItemDao.createOrderItemEntity(orderItemEntity);
    }

    /* To fetch the list of orders based on customer UUID */
    public List<OrderEntity> getOrdersByCustomers(String customerUUID) {
        return orderDao.getOrdersByCustomers(customerDao.getCustomerByUUID(customerUUID));
    }

}
