package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.common.AbstractRepositoryFactory;
import am.trainings.palmetto.common.RepositoryTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomerRepositoryFactory extends AbstractRepositoryFactory {

    private static final Map<RepositoryTypes, CustomerRepository> CUSTOMER_REPOSITORY_MAP = new HashMap<>();

    static {
        CUSTOMER_REPOSITORY_MAP.put(RepositoryTypes.IN_MEMORY, InMemoryCustomerRepository.getInstance());
    }

    public CustomerRepository getRepositoryByType(RepositoryTypes type) {

        CustomerRepository customerRepository = CUSTOMER_REPOSITORY_MAP.get(type);
        if (Objects.isNull(customerRepository)) {
            throw new IllegalStateException("No customer repository found for type " + type);
        }

        return customerRepository;
    }
}