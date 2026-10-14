package am.trainings.palmetto.common;


import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.customer.repository.CustomerRepositoryFactory;
import am.trainings.palmetto.customer.repository.OrderRepository;
import am.trainings.palmetto.customer.repository.OrderRepositoryFactory;


import java.util.Map;
import java.util.NoSuchElementException;
public final class ApplicationContextHolder {
    private final RepositoryTypes repositoryType = RepositoryTypes.FILE_REPOSITORY;
    private static final Map<Class<?>, Object> CONTEXT = Map.of(
            CustomerRepository.class, new CustomerRepositoryFactory(),
            OrderRepository.class, new OrderRepositoryFactory()
    );
    public <T> T getObject(Class<T> clazz) {
        Object entry = CONTEXT.get(clazz);

        if (entry == null) {
            throw new NoSuchElementException("No object registered for type: " + clazz.getName());
        }

        if (entry instanceof AbstractRepositoryFactory factory) {
            Object repo = factory.getRepositoryByType(repositoryType);
            return clazz.cast(repo); // safer than (T)
        }
        // allow direct instances too
        return clazz.cast(entry);
    }


}