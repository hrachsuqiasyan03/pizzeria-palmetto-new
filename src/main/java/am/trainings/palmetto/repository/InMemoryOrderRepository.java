package am.trainings.palmetto.repository;

import am.trainings.palmetto.customer.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
// dao - data access object
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> orders = new HashMap<>();


    private Long initialOrderId = 1L;

    public long save(Order order) {
        Long orderId = generateOrderNumber();
        order.setOrderId(orderId);
        orders.put(orderId, order);
        return orderId;
    }


    private Long generateOrderNumber() {
        return initialOrderId++;
    }

}