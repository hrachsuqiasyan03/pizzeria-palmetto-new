package am.trainings.palmetto;
import am.trainings.palmetto.common.ApplicationContextHolder;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.customer.repository.InMemoryCustomerRepository;
import am.trainings.palmetto.customer.service.OrderServiceImpl;
import am.trainings.palmetto.pizza.Pizza;
import am.trainings.palmetto.pizza.Ingredients;
import am.trainings.palmetto.pizza.PizzaType;
import am.trainings.palmetto.pizza.ReceiptPrinter;

import java.util.*;

/**
 * This is the main class of the Pizzeria application.
 * It creates a customer and an order, adds ingredients to the pizza,
 * and prints the final pizza details.
 */
public class Pizerria {
    private static InMemoryCustomerRepository customerRepository;

    public static void main(String[] args) {
        initializeApplicationContext();

        Scanner scanner = new Scanner (System.in);

        System.out.print("Enter your Fullname: ");
        String name = scanner.nextLine ();

        System.out.print("Enter your Username: ");
        String username = scanner.nextLine ();

        Customer customer = new Customer (name, username);

        String s = customerRepository.registerCustomer(customer);
        System.out.println("Customer registered as " + s);

        Map<Pizza, Integer> pizzaTypeCount = new HashMap<> ();
        Boolean isAdding = true;
        do{
            System.out.println ("Choose pizza type:");
            System.out.println ("Regular type - 0 , Calzone type - 1");

            PizzaType pizzaTypes = PizzaType.values ()[scanner.nextInt ()];
            scanner.nextLine ();

            System.out.println ("How many pizzas do you want to order? ");
            int pizzasCount = scanner.nextInt ();
            scanner.nextLine ();

            System.out.println ("Which pizza would you like to order? ");
            System.out.println ("Margherita, Pizzette, Pepperoni, Romana, Parmigiana");
            String pizzaName = scanner.nextLine ();

            List<Ingredients> ingredients = new ArrayList<> ();
            System.out.println ("Add ingrediends");
            System.out.println ("Tomato paste (0), Cheese (1), Salami (2), Bacon (3), Garlic (4), Corn (5), Pepperoni (6), Olives (7)");

            int ingredient;
            while(true){
                ingredient = scanner.nextInt ();
                 if(ingredient == -1){
                     break;
                 }
                 if(ingredient >= 0 && ingredient <Ingredients.values ().length){
                     ingredients.add (Ingredients.values ()[ingredient]);
                 }else{
                     System.out.println ("Incorrect ingredient");
                 }
            }
            scanner.nextLine ();

            Ingredients[] ingredientsArray
                    = ingredients.toArray(new Ingredients[0]);
            Pizza pizzas = new Pizza (pizzaName, pizzaTypes.name (), ingredientsArray);
            pizzaTypeCount.put(pizzas , pizzasCount);

            System.out.println ("Do you want to order another pizza? Yes or No ");
            String response = scanner.nextLine ();

            isAdding = response.equalsIgnoreCase ("Yes");
        }while (isAdding);

        double finalPizzaPrice = 0;
        for(Map.Entry<Pizza, Integer> entry : pizzaTypeCount.entrySet()) {
            Pizza pizza = entry.getKey ();
            Integer count = entry.getValue ();
            finalPizzaPrice += pizza.getIngredientPrice () * count;
        }
        ApplicationContextHolder contextHolder = new ApplicationContextHolder();
        OrderServiceImpl service = new OrderServiceImpl (contextHolder);
        long orderId = service.orderPizza (username, pizzaTypeCount);
        Order order = new Order (customer.getCustomerId (), pizzaTypeCount);
        ReceiptPrinter.printReceipt (order);
    }
    private static void initializeApplicationContext() {
        customerRepository = InMemoryCustomerRepository.getInstance();
    }
}