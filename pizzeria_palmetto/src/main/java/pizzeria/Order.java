package pizzeria;

public class Order {
    private final int orderNumber;
    private static int nextOrderNumber = 10001;
    private final int customerOrder;
    private int pizzaCount = 0;
    private final Pizza[] pizzas = new Pizza[10];
    //constructor
    public Order(int customerOrder) {
        this.orderNumber = nextOrderNumber++;
        this.customerOrder = customerOrder;
    }
    //getter
    public int getOrderNumber() {return orderNumber;}
    public int getCustomerOrder() {return customerOrder;}

    public void addPizza(Pizza pizza){
        if(pizza.getName().length() < 4 || pizza.getName().length() > 20){
            pizza.setName("Customer_name_" + pizzaCount);
        }
        pizzas[pizzaCount] = pizza;
        pizzaCount++;
    }
    public void display() {
        for (Pizza pizza : pizzas) {
            // stop looping when we hit an empty slot
            if(pizza == null) break;
            System.out.println("[" + orderNumber + " : "
                    + customerOrder + " : "
                    + pizza.getName() + " : "
                    + pizza.getQuantity() + "]");
        }
    }
    public double calculateTotal(){
        double amount = 0;
        for(Pizza p : pizzas){
            //skip empty slots
            if( p == null) continue;
            amount += p.calculatePrices() * p.getQuantity();
        }
        return Math.round(amount);
    }
    public void printCheck(){
        System.out.println("*****************");
        System.out.println("Order: " + getOrderNumber());
        System.out.println("Client: " + getCustomerOrder());
        // loop through each pizza in the order
        for(Pizza pizza : pizzas){
            if(pizza == null) continue;
            System.out.println("Name: " + pizza.getName());
            System.out.println("---------------");
            // print base price depending on pizza type
            if(pizza.getType().equals("Calzone")){
                System.out.println("Pizza base (Calzone) 1.50 $" );
            }else{
                System.out.println("Pizza base 1.0 $");
            }
            // loop through this pizza's ingredients
            for(String s : pizza.getIngredients()){
                if( s == null) continue;
                // find the matching ingredient in the allowed list
                for (int i = 0; i < Pizza.getAllIngredients().length; i++) {
                    if(Pizza.getAllIngredients()[i].equals(s)){
                        System.out.println( s + " " + Pizza.getAllPrices()[i] + " $");
                    }
                }
            }
            System.out.println("---------------");
            // multiply price of one pizza by quantity, round to 2 decimal places
            System.out.println("Amount: " + Math.round(pizza.calculatePrices() * pizza.getQuantity()*100) / 100.0 + " $");
            System.out.println("Quantity: " + pizza.getQuantity());
            System.out.println("---------------");
        }
        System.out.println("Total amount: " + Math.round(calculateTotal())+ " $");
        System.out.println("*****************");
    }
}