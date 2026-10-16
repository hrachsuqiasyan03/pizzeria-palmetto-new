package am.trainings.palmetto.pizza;

import am.trainings.palmetto.customer.Order;

import java.time.LocalTime;
import java.util.Map;

public class ReceiptPrinter {

    public static void printReceipt(Order order) {
        LocalTime time = LocalTime.now();
        double totalAmount = 0;

        System.out.println("========================================");
        System.out.println("                 ORDER");
        System.out.println("Client: " + order.getCustomerId());
        System.out.println ("Date: " + time);
        System.out.println("========================================");

        for (Map.Entry<Pizza, Integer> entry : order.getPizzas().entrySet()) {
            Pizza pizza = entry.getKey();
            int quantity = entry.getValue();

            double basePrice = PizzaType.valueOf(pizza.getType()).getPrice();
            System.out.println("\nName: " + pizza.getName());
            System.out.printf("Pizza Base (%s) %.2f $%n", pizza.getType(), basePrice);

            for (Ingredients ingredient : pizza.getIngredients()) {
                System.out.printf("%s %.2f $%n", ingredient.name(), ingredient.getPrice());
            }
            double unitPrice = pizza.getIngredientPrice ();
            double subtotal = unitPrice * quantity;

            System.out.printf("Amount: %.2f $%n", unitPrice);
            System.out.println("Quantity: " + quantity);
            totalAmount += subtotal;
        }
        System.out.println("----------------------------------------");
        System.out.printf("Total amount: %.2f $%n", totalAmount);
        System.out.println("========================================");
    }


}
