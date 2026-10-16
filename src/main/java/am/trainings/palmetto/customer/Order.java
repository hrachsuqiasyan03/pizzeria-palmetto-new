package am.trainings.palmetto.customer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


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

    @JsonCreator
    public Order(
            @JsonProperty("customerId") Long customerId,
            @JsonProperty("pizzas") Map<Pizza, Integer> pizzas) {
        this.customerId = customerId;
        this.pizzas = pizzas;
    }

    public void addPizza(Pizza pizza, int pizzasCount) {
        pizzas.put(pizza, pizzasCount);
    }
    public void printCheck(){

    }
}