package am.trainings.palmetto;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.repository.InMemoryCustomerRepository;

import java.util.Scanner;

/**
 * This is the main class of the Pizzeria application.
 * It creates a customer and an order, adds ingredients to the pizza,
 * and prints the final pizza details.
 */
public class Pizerria {

    private static InMemoryCustomerRepository customerRepository;

    public static void main(String[] args) {
        initializeApplicationContext();

        Scanner scanner = new Scanner (System.in);

        System.out.print("Enter your Fullname: ");
        String name = scanner.nextLine ();

        System.out.print("Enter your Username: ");
        String username = scanner.nextLine ();

        Customer customer = new Customer (name, username);

        String s = customerRepository.registerCustomer(customer);

        System.out.println("Customer registered as " + s);

        scanner.close ();
    }

    private static void initializeApplicationContext() {
        customerRepository = InMemoryCustomerRepository.getInstance();
    }
}