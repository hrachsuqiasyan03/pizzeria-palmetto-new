package am.trainings.palmetto;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.repository.InMemoryCustomerRepository;

/**
 * This is the main class of the Pizzeria application.
 * It creates a customer and an order, adds ingredients to the pizza,
 * and prints the final pizza details.
 */
public class Pizerria {

    private static InMemoryCustomerRepository customerRepository;

    public static void main(String[] args) {
        initializeApplicationContext();

        Customer customer = new Customer("Hrach" , "Suqiasyan");

        String s = customerRepository.registerCustomer(customer);

        System.out.println("Customer registered as " + s);
    }

    private static void initializeApplicationContext() {
        customerRepository = InMemoryCustomerRepository.getInstance();
    }
}