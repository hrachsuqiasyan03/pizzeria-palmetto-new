package pizzeria;

public class PizzaMain {
    public static void main(String[] args) {

        Order order1 = new Order(7717);
        Pizza pizza1 = new Pizza("Margarita", "regular", 2);
        order1.addPizza(pizza1);
        pizza1.addIngredient("Tomato paste");
        pizza1.addIngredient("Garlic");
        pizza1.addIngredient("Bacon");

        Pizza pizza2 = new Pizza("PepperoniOro", "regular", 3);
        pizza2.addIngredient("Tomato paste");
        pizza2.addIngredient("Cheese");
        pizza2.addIngredient("Pepperoni");
        pizza2.addIngredient("Olives");
        order1.addPizza(pizza2);
        order1.printCheck();
    }
}
