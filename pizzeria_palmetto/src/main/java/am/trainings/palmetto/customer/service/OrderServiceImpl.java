package am.trainings.palmetto.customer.service;

import am.trainings.palmetto.common.ApplicationContextHolder;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.customer.exceptions.UserNotFoundException;
import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.customer.repository.OrderRepository;
import am.trainings.palmetto.pizza.Pizza;

import java.util.Map;

public class OrderServiceImpl implements OrderService {


    private final CustomerRepository customerRepository;


    private final OrderRepository orderRepository;

    public OrderServiceImpl(ApplicationContextHolder contextHolder) {
        this.customerRepository = contextHolder.getObject(CustomerRepository.class);
        this.orderRepository = contextHolder.getObject(OrderRepository.class);
    }

    @Override
    public long orderPizza(String username, Map<Pizza, Integer> pizzas) {

        Customer customer = customerRepository.findCustomerByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        Order order = new Order(customer.getCustomerId(), pizzas);
        return orderRepository.save(order);
    }
}
