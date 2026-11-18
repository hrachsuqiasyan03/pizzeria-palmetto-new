package am.trainings.palmetto.service;

import am.trainings.palmetto.customer.Customer;

import java.util.Optional;

public interface CustomerService {

    String registerCustomer(Customer customer);
    Optional<Customer> findCustomerByUsername(String username);
}
