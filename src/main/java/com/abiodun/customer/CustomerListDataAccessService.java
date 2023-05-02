package com.abiodun.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer daniel = new Customer(
                1,
                "Daniel",
                "daniel@abiodun.dev",
                10
        );
        Customer joshua = new Customer(
                2,
                "Joshua",
                "joshua@abiodun.dev",
                5
        );

        customers.add(daniel);
        customers.add(joshua);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        return customers.stream().filter(customer -> customer.getId().equals(customerId))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customers = customers.stream()
                .filter(c -> !c.getId().equals(customer.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }
}
