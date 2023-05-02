package com.abiodun.customer;

import com.abiodun.exception.DuplicateResourceException;
import com.abiodun.exception.RequestValidationException;
import com.abiodun.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer customerId) {
        return customerDao.selectCustomerById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer with id: [%s] not found".formatted(customerId)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        // check if email exists
        String email = customerRegistrationRequest.email();
        if(customerDao.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException("Customer with id: [%s] already exists".formatted(email));
        }
        // otherwise, add
        customerDao.insertCustomer(
                new Customer(
                        customerRegistrationRequest.name(),
                        customerRegistrationRequest.email(),
                        customerRegistrationRequest.age()
                )
        );
    }

    public void deleteCustomer(Integer customerId) {
        Customer customer = getCustomer(customerId);
        customerDao.deleteCustomer(customer);
    }

    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest,
                               Integer customerId) {
        Customer customer = getCustomer(customerId);
        boolean changes = false;
        if(customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(customer.getAge())) {
            changes = true;
            customer.setAge(customerUpdateRequest.age());
        }
        if(customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())) {
            changes = true;
            customer.setName(customerUpdateRequest.name());
        }
        if(customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(customer.getEmail())) {
            if(customerDao.existsPersonWithEmail(customerUpdateRequest.email())) {
                throw new DuplicateResourceException("Customer with id: [%s] already exists".formatted(customerUpdateRequest.email()));
            }
            changes = true;
            customer.setName(customerUpdateRequest.email());
        }
        if(!changes) {
            throw new RequestValidationException("No changes found");
        }
        customerDao.updateCustomer(customer);

    }
}
