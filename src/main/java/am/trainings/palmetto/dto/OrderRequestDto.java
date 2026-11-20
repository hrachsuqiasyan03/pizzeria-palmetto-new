package am.trainings.palmetto.dto;

import java.util.Map;

public class OrderRequestDto {

    private String username;
    private Map<String, Integer> pizzas;

    public OrderRequestDto(String username, Map<String, Integer> pizzas) {
        this.username = username;
        this.pizzas = pizzas;
    }

    public OrderRequestDto() {}

    public String getUsername() {
        return username;
    }
    public Map<String, Integer> getPizzas() {
        return pizzas;
    }
}
