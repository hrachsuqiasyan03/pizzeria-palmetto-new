package am.trainings.palmetto.service;

import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.pizza.Pizza;

import java.util.Map;

public interface OrderService {
    Order orderPizza(String username, Map<Pizza, Integer> pizzas);


}
