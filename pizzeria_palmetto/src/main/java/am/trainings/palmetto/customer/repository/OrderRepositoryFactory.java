package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.common.AbstractRepositoryFactory;
import am.trainings.palmetto.common.RepositoryTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderRepositoryFactory  extends AbstractRepositoryFactory {
    private static final Map<RepositoryTypes, OrderRepository> ORDER_REPOSITORY_MAP = new HashMap<> ();

    static {
        ORDER_REPOSITORY_MAP.put(RepositoryTypes.IN_MEMORY, InMemoryOrderRepository.getInstance());
    }

    public OrderRepositoryFactory getInstance() {
        return new OrderRepositoryFactory();
    }

    public OrderRepository getRepositoryByType(RepositoryTypes type) {

        OrderRepository orderRepository = ORDER_REPOSITORY_MAP.get(type);
        if (Objects.isNull(orderRepository)) {
            throw new IllegalStateException("No order repository found for type " + type);
        }

        return orderRepository;
    }
}
