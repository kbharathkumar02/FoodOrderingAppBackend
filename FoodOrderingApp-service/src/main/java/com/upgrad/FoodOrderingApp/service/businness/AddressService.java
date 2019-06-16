package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CustomerAddressDao customerAddressDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private OrderDao ordersDao;

    /* business logic to save address*/
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity, CustomerEntity customerEntity) throws SaveAddressException {

        // Validation for required fields
        if (
                addressEntity.getFlatBuilNo().equals("") ||
                        addressEntity.getLocality().equals("") ||
                        addressEntity.getCity().equals("") ||
                        addressEntity.getPincode().equals("") ||
                        addressEntity.getState() == null ||
                        addressEntity.getActive() == null
        ) {
            throw new SaveAddressException("SAR-001", "No field can be empty");
        }

        // Check the pincode format and throw exception in case of unexpected format

        if(!addressEntity.getPincode().matches("^[1-9][0-9]{5}$")){
            throw new SaveAddressException("SAR-002", "Invalid pincode");
        }

        AddressEntity createdAddressEntity = addressDao.createAddress(addressEntity);

        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        customerAddressEntity.setCustomer(customerEntity.getId());
        customerAddressEntity.setAddress(createdAddressEntity.getId());
        customerAddressDao.createCustomerAddress(customerAddressEntity);

        return createdAddressEntity;
    }

    /* fetches state entity based on state uuid*/
    public StateEntity getStateByUUID(String stateUUID) throws AddressNotFoundException {
        StateEntity stateEntity = stateDao.getStateByUUID(stateUUID);
        if (stateEntity == null) {
            throw new AddressNotFoundException("ANF-002", "No state by this state id");
        }
        return stateEntity;
    }

    /* fetches list of addresses of a specific customer based on the customer entity*/
    public List<AddressEntity> getAllAddress(CustomerEntity customerEntity) {
        return customerEntity.getAddresses();
    }

    /* to fetch the list of all states*/
    public List<StateEntity> getAllStates() {
        return stateDao.getAllStates();
    }

    /* fetches the address entity based on address uuid but will allow to view only if the authorized customer tried to access else will throw an exception*/
    public AddressEntity getAddressByUUID(String addressUUID, CustomerEntity customerEntity) throws AddressNotFoundException, AuthorizationFailedException {
        AddressEntity addressEntity = addressDao.getAddressByUUID(addressUUID);

        if (addressEntity == null) {
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        }

        if (!addressEntity.getCustomer().getUuid().equals(customerEntity.getUuid())) {
            throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
        }

        return addressEntity;
    }

    /* Deletes the address entity only if orders on that address from past are null or 0 else will set the address as inactive*/
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity deleteAddress(AddressEntity addressEntity) {
        List<OrderEntity> ordersEntityList = ordersDao.getOrdersByAddress(addressEntity);
        if (ordersEntityList == null || ordersEntityList.isEmpty()) {
            return addressDao.deleteAddressEntity(addressEntity);
        }

        addressEntity.setActive(0);
        return addressDao.updateAddressEntity(addressEntity);
    }
}
