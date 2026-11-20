package am.trainings.palmetto.service;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.exceptions.UserNotFoundException;
import am.trainings.palmetto.repository.CustomerRepository;
import am.trainings.palmetto.repository.OrderRepository;
import am.trainings.palmetto.pizza.Pizza;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }
    @Override
    public Order orderPizza(String username, Map<Pizza, Integer> pizzas) {

        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseGet(() -> customerRepository.findCustomerByUsername ("anonymous")
                .orElseThrow(() -> new UserNotFoundException("User not found")));

        Order order = new Order(customer.getCustomerId(), pizzas);

        orderRepository.save(order);
        return order;
    }
}
