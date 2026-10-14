package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.exceptions.DublicateUsernameException;
import am.trainings.palmetto.json.JsonWriter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerFileRepository  implements CustomerRepository{

    private final Map<String, Customer> customers = new HashMap<> ();

    private final File file = resolveDataFile("customer.json");

    private final ObjectMapper mapper = new ObjectMapper();

    private long customerId = 1;

    private static File resolveDataFile(String name) {
        File moduleRelative = new File("src/main/java/am/trainings/palmetto/data", name);
        if (moduleRelative.getParentFile().exists()) {
            return moduleRelative;
        }
        return new File("pizzeria_palmetto/src/main/java/am/trainings/palmetto/data", name);
    }

    public CustomerFileRepository() {
        loadCustomers();
    }

    private void loadCustomers() {
        if(!file.exists()) {
            return;
        }
        try{
            Map<String, Customer> loadedCustomer = mapper.readValue (file, new TypeReference<Map<String, Customer>> () {});
            customers.putAll(loadedCustomer);

            for(Customer customer : customers.values()){
                customerId = Math.max(customerId, customer.getCustomerId() + 1);
            }
        }catch (IOException e){
            throw new RuntimeException("Failed to read JSON file: " + file, e);
        }
    }

    @Override
    public String registerCustomer(Customer customer) {
        String username = customer.getUsername();

        if(customers.containsKey(username)) {
            throw new DublicateUsernameException ("Username already exists " + username);
        }
        customer.setCustomerId (customerId++);
        customers.put(username, customer);
        saveCustomer();
        return username;
    }

    public void saveCustomer() {
        try{
            File parent = file.getParentFile();

            if(parent != null && !parent.exists()){
                parent.mkdirs();
            }
            JsonWriter.getInstance ().write(customers,file.getPath ());
        }catch (RuntimeException e){
            throw new  RuntimeException("Failed to save customer " + customerId, e);
        }
    }
    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        return Optional.ofNullable(customers.get(username));
    }
}
