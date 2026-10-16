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

    private final File file = resolveDataFile("orders.json");

    private final ObjectMapper mapper = new ObjectMapper();

    private long orderId = 1L;

    private static File resolveDataFile(String name) {
        File workingDirectory = new File(System.getProperty("user.dir"));
        File moduleData = new File(workingDirectory, "src/main/resources/data/" + name);
        if (new File(workingDirectory, "pizzeria_palmetto/pom.xml").isFile()) {
            moduleData = new File(workingDirectory,
                    "pizzeria_palmetto/src/main/resources/data/" + name);
        }
        return moduleData;
    }

    public OrderFileRepository() {
        loadOrders();
    }

    private void loadOrders() {
        if (!file.exists() || file.length() == 0) {
            return;
        }

        try {
            Map<String, com.fasterxml.jackson.databind.JsonNode> loadedOrders =
                    mapper.readValue(file, new TypeReference<Map<String, com.fasterxml.jackson.databind.JsonNode>>() {});

            for (Map.Entry<String, com.fasterxml.jackson.databind.JsonNode> entry : loadedOrders.entrySet()) {
                long id = Long.parseLong(entry.getKey());
                com.fasterxml.jackson.databind.JsonNode data = entry.getValue();
                Long customerId = data.path("customerId").isNull()
                        ? null : data.path("customerId").asLong();
                Map<Pizza, Integer> pizzas = readPizzas(data.path("pizzas"));
                Order order = new Order(customerId, pizzas);
                long savedOrderId = data.path("orderId").asLong(id);
                order.setOrderId(savedOrderId);
                orders.put(id, order);
                orderId = Math.max(orderId, Math.max(id, savedOrderId) + 1);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read orders JSON file: " + file,
                    e
            );
        }
    }

    private Map<Pizza, Integer> readPizzas(com.fasterxml.jackson.databind.JsonNode pizzasNode) {
        Map<Pizza, Integer> pizzas = new HashMap<>();
        if (pizzasNode.isArray()) {
            for (com.fasterxml.jackson.databind.JsonNode item : pizzasNode) {
                pizzas.put(readPizza(item), item.path("quantity").asInt());
            }
        } else if (pizzasNode.isObject()) {
            // Older files serialized Pizza objects as map keys using Pizza.toString().
            for (Map.Entry<String, com.fasterxml.jackson.databind.JsonNode> entry :
                    mapper.convertValue(pizzasNode, new TypeReference<Map<String, com.fasterxml.jackson.databind.JsonNode>>() {}).entrySet()) {
                String key = entry.getKey();
                String name = valueFromLegacyKey(key, "name");
                String type = valueFromLegacyKey(key, "type");
                String ingredientsText = valueFromLegacyKey(key, "ingredients");
                List<Ingredients> ingredients = new ArrayList<>();
                if (ingredientsText != null) {
                    for (String ingredient : ingredientsText.replace("[", "").replace("]", "").split(",\\s*")) {
                        if (!ingredient.isBlank()) ingredients.add(Ingredients.valueOf(ingredient));
                    }
                }
                pizzas.put(new Pizza(name, type, ingredients.toArray(new Ingredients[0])), entry.getValue().asInt());
            }
        }
        return pizzas;
    }

    private Pizza readPizza(com.fasterxml.jackson.databind.JsonNode item) {
        List<Ingredients> ingredients = new ArrayList<>();
        for (com.fasterxml.jackson.databind.JsonNode ingredient : item.path("ingredients")) {
            ingredients.add(Ingredients.valueOf(ingredient.asText()));
        }
        return new Pizza(item.path("name").asText(), item.path("type").asText(),
                ingredients.toArray(new Ingredients[0]));
    }

    private String valueFromLegacyKey(String key, String property) {
        String marker = property + "='";
        int start = key.indexOf(marker);
        if (start < 0) return null;
        start += marker.length();
        int end = key.indexOf('\'', start);
        if (end < 0) return null;
        return key.substring(start, end);
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
            File parent = file.getAbsoluteFile().getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new IOException("Could not create order data directory: " + parent);
            }

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file.getAbsoluteFile(), dataToSave);

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
