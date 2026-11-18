package am.trainings.palmetto;

import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.service.CustomerService;
import am.trainings.palmetto.service.OrderService;
import am.trainings.palmetto.pizza.Pizza;
import am.trainings.palmetto.pizza.Ingredients;
import am.trainings.palmetto.pizza.PizzaType;
import am.trainings.palmetto.pizza.ReceiptPrinter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import am.trainings.palmetto.config.SpringConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * This is the main class of the Pizzeria application.
 * It creates a customer and an order, adds ingredients to the pizza,
 * and prints the final pizza details.
 */

public class Pizzeria {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext (SpringConfig.class)) {

            CustomerService customerService =
                    context.getBean (CustomerService.class);
            OrderService orderService =
                    context.getBean (OrderService.class);

            Scanner scanner = new Scanner (System.in);
            System.out.println ("Do you have registered account?");
            System.out.println ("Yes or No");
            String response = scanner.nextLine ().trim ();

            String username;
            Customer customer;

            if (response.equalsIgnoreCase ("No")) {
                System.out.print ("Enter your Fullname: ");
                String name = scanner.nextLine ().trim ();

                System.out.print ("Enter your Username: ");
                username = scanner.nextLine ();

                customer = new Customer (name, username);
                String registeredUsername = customerService.registerCustomer (customer);
                System.out.println ("Customer registered as " + registeredUsername);

            } else if (response.equalsIgnoreCase ("Yes")) {
                System.out.println ("Write your username");
                username = scanner.nextLine ();

                Optional<Customer> findCustomerByUsername = customerService.findCustomerByUsername (username);

                while (findCustomerByUsername.isEmpty ()) {
                    System.out.print ("Customer not found");
                    username = scanner.nextLine ().trim ();

                    findCustomerByUsername = customerService.findCustomerByUsername (username);
                }

                customer = findCustomerByUsername.get ();
                System.out.println ("Customer registered as " + customer);

            } else {
                System.out.println ("Invalid input");
                return;
            }

            Map<Pizza, Integer> pizzaQuantities = new HashMap<> ();
            boolean isAdding;
            do {
                System.out.println ("Choose pizza type:");
                System.out.println ("Regular type - 0 , Calzone type - 1");

                int typeIndex = scanner.nextInt ();
                if (typeIndex < 0 || typeIndex >= PizzaType.values ().length) {
                    System.out.println ("Incorrect pizza type");
                    return;
                }
                PizzaType pizzaTypes = PizzaType.values ()[typeIndex];

                System.out.println ("How many pizzas do you want to order? ");
                int pizzasCount = scanner.nextInt ();
                scanner.nextLine ();

                System.out.println ("Which pizza would you like to order? ");
                System.out.println ("Margherita, Pizzette, Pepperoni, Romana, Parmigiana");
                String pizzaName = scanner.nextLine ();

                List<Ingredients> ingredients = new ArrayList<> ();
                System.out.println ("Add ingredients");
                System.out.println ("Tomato paste (0), Cheese (1), Salami (2), Bacon (3), Garlic (4), Corn (5), Pepperoni (6), Olives (7)");
                System.out.println ("Press -1 if you don't want to add another ingredient");

                int ingredient;
                while (true) {
                    ingredient = scanner.nextInt ();
                    if (ingredient == -1) {
                        break;
                    }
                    if (ingredient >= 0 && ingredient < Ingredients.values ().length) {
                        ingredients.add (Ingredients.values ()[ingredient]);
                    } else {
                        System.out.println ("Incorrect ingredient");
                    }
                }

                scanner.nextLine ();

                Ingredients[] ingredientsArray
                        = ingredients.toArray (new Ingredients[0]);
                Pizza pizza = new Pizza (pizzaName, pizzaTypes.name (), ingredientsArray);
                pizzaQuantities.put (pizza, pizzasCount);

                System.out.println ("Do you want to order another pizza? Yes or No ");
                String response1 = scanner.nextLine ();

                isAdding = response1.equalsIgnoreCase ("Yes");

            } while (isAdding);

            Order order = orderService.orderPizza (username, pizzaQuantities);
            ReceiptPrinter.printReceipt (order);
        }
    }
}
