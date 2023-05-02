package com.abiodun.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer customerId);
    void insertCustomer(Customer customer);
    boolean existsPersonWithEmail(String email);
    void deleteCustomer(Customer customer);

    void updateCustomer(Customer customer);
}
