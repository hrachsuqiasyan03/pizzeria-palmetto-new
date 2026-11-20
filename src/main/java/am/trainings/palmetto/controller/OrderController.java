package am.trainings.palmetto.controller;

import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.dto.OrderRequestDto;
import am.trainings.palmetto.pizza.Pizza;
import am.trainings.palmetto.pizza.PizzaType;
import am.trainings.palmetto.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Map<Pizza, Integer> pizzaMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : orderRequestDto.getPizzas().entrySet()) {
            pizzaMap.put(new Pizza(entry.getKey(), PizzaType.REGULAR.name(), null), entry.getValue());
        }
        return orderService.orderPizza(orderRequestDto.getUsername(), pizzaMap);
    }
}
