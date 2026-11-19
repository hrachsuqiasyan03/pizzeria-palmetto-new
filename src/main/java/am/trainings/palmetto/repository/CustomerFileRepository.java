package am.trainings.palmetto.repository;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.exceptions.DublicateUsernameException;
import am.trainings.palmetto.json.JsonWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Path;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class CustomerFileRepository  implements CustomerRepository{

    private final Map<String, Customer> customers = new HashMap<> ();

    private final File file;

    private final ObjectMapper mapper = new ObjectMapper();

    private long customerId = 1;

    private final JsonWriter jsonWriter;



    public CustomerFileRepository(
            JsonWriter jsonWriter,
            @Value ("${pizzeria.data.directory}") String dataDirectory) {

        this.jsonWriter = jsonWriter;
        this.file = Path.of(dataDirectory, "customers.json").toFile();
        loadCustomers ();
    }

    private void loadCustomers() {
        if (!file.exists()) {
            return;
        }
        try {
            Map<String, Customer> loadedCustomer = mapper.readValue (file, new TypeReference<Map<String, Customer>> () {});
            customers.putAll(loadedCustomer);

            for (Customer customer : customers.values()) {
                customerId = Math.max(customerId, customer.getCustomerId() + 1);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file: " + file, e);
        }
    }

    @Override
    public String registerCustomer(Customer customer) {
        String username = customer.getUsername();

        if (customers.containsKey(username)) {
            throw new DublicateUsernameException ("Username already exists " + username);
        }

        customer.setCustomerId (customerId++);
        customers.put(username, customer);
        saveCustomer();
        return username;
    }

    public void saveCustomer() {
        try {
            File parent = file.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            jsonWriter.write(customers,file.getPath ());
        } catch (RuntimeException e) {
            throw new  RuntimeException("Failed to save customer " + customerId, e);
        }
    }
    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        return Optional.ofNullable(customers.get(username));
    }
}
