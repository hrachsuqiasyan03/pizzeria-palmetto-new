package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.exceptions.DublicateUsernameException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCustomerRepository  implements CustomerRepository {
    private Map<String, Customer> customers = new HashMap<> ();
    private long customerId = 0;
    private static InMemoryCustomerRepository INSTANCE;

    private InMemoryCustomerRepository() {
        System.out.println ("Initializing only once");
    }

    public static InMemoryCustomerRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryCustomerRepository ();
        }
        return INSTANCE;
    }
    public String registerCustomer(Customer customer) {
        boolean isCustomerExist = customers.containsKey (customer.getUsername());
        if(isCustomerExist) {
            throw new DublicateUsernameException ("Username" + customer.getUsername () + "already exsist");
        }
        long customerId = generateCustomerNumber ();
        customer.setCustomerId(customerId);
        String username = customer.getUsername ();
        customers.put(customer.getUsername(),customer);
        return username;
    }

    public Optional<Customer> findCustomerByUsername(String username) {
        return Optional.ofNullable(customers.get (username));
    }

    private long generateCustomerNumber() {
        return customerId++;
    }
}
