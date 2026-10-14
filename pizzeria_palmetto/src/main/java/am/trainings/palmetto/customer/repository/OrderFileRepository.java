package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.pizza.Pizza;
import am.trainings.palmetto.pizza.Ingredients;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFileRepository implements OrderRepository {

    private final Map<Long, Order> orders = new HashMap<>();

    private final File file = new File(
            "src/main/java/am/trainings/palmetto/data/orders.json"
    );

    private final ObjectMapper mapper = new ObjectMapper();

    private long orderId = 1L;

    public OrderFileRepository() {
        loadOrders();
    }

    private void loadOrders() {
        if (!file.exists() || file.length() == 0) {
            return;
        }

        try {
            Map<Long, OrderData> loadedOrders = mapper.readValue(
                    file,
                    new TypeReference<Map<Long, OrderData>>() {}
            );

            for (Map.Entry<Long, OrderData> entry
                    : loadedOrders.entrySet()) {

                OrderData data = entry.getValue();

                Map<Pizza, Integer> pizzas = new HashMap<>();

                for (PizzaEntry pizzaEntry : data.pizzas) {
                    Pizza pizza = new Pizza(
                            pizzaEntry.name,
                            pizzaEntry.type,
                            pizzaEntry.ingredients.toArray(
                                    new Ingredients[0]
                            )
                    );

                    pizzas.put(pizza, pizzaEntry.quantity);
                }

                Order order = new Order(data.customerId, pizzas);
                order.setOrderId(data.orderId);

                orders.put(entry.getKey(), order);

                orderId = Math.max(
                        orderId,
                        data.orderId + 1
                );
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read orders JSON file: " + file,
                    e
            );
        }
    }

    @Override
    public long save(Order order) {
        long id = orderId++;

        order.setOrderId(id);
        orders.put(id, order);

        saveOrders();

        return id;
    }

    private void saveOrders() {
        Map<Long, OrderData> dataToSave = new HashMap<>();

        for (Map.Entry<Long, Order> entry : orders.entrySet()) {
            Order order = entry.getValue();

            OrderData data = new OrderData();
            data.orderId = order.getOrderId();
            data.customerId = order.getCustomerId();

            for (Map.Entry<Pizza, Integer> pizzaEntry
                    : order.getPizzas().entrySet()) {

                Pizza pizza = pizzaEntry.getKey();

                PizzaEntry pizzaData = new PizzaEntry();
                pizzaData.name = pizza.getName();
                pizzaData.type = pizza.getType();
                pizzaData.ingredients = pizza.getIngredients();
                pizzaData.quantity = pizzaEntry.getValue();

                data.pizzas.add(pizzaData);
            }

            dataToSave.put(entry.getKey(), data);
        }

        try {
            File parent = file.getParentFile();

            if (parent != null) {
                parent.mkdirs();
            }

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, dataToSave);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to save orders JSON file: " + file,
                    e
            );
        }
    }

    // JSON-ի համար միջանկյալ տվյալների դասեր

    public static class OrderData {
        public Long orderId;
        public Long customerId;
        public List<PizzaEntry> pizzas = new ArrayList<>();
    }

    public static class PizzaEntry {
        public String name;
        public String type;
        public List<Ingredients> ingredients = new ArrayList<>();
        public int quantity;
    }
}