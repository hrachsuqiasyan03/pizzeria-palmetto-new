package am.trainings.palmetto.pizza;


public enum PizzaType {

    REGULAR(1),
    CALZONE(0.5);

    PizzaType(double value) {
        price = value;
    }

    private double price;

    public double getPrice() {
        return price;
    }
}
