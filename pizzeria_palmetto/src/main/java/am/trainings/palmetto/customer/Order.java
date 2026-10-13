package am.trainings.palmetto.customer;


import am.trainings.palmetto.pizza.Pizza;
import lombok.Data;

import java.util.Map;

/**
 * add ability to order several types of pizzas
 */
@Data
public class Order {

    private Long orderId;
    private final Long customerId;
    private final Map<Pizza, Integer> pizzas;

    public Order( Long customerId, Map<Pizza, Integer> pizzas) {
        this.customerId = customerId;
        this.pizzas = pizzas;

    }

    public void addPizza(Pizza pizza, int pizzasCount) {
        pizzas.put(pizza, pizzasCount);
    }
    public void printCheck(){

    }
}