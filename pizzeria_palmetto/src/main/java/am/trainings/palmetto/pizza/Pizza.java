package am.trainings.palmetto.pizza;

/**
 * YAGNI, KISS, DRY
 */
public class Pizza {

    public static final Pizza REGULAR = new Pizza("Regular", PizzaType.REGULAR.name(), new String[7]);

    private String name;
    private String type;
    private String[] ingredients;
    private int ingredientsCount;

    public Pizza(String name, String type, String[] ingredients) {
        this.name = name;
        this.type = type;
        this.ingredients = ingredients;
        for (String ingredient : ingredients) {
            if (ingredient != null) {
                ingredientsCount++;
            }
        }
    }

    public String getName() {
        return name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void addIngredient(String ingredient) {
        ingredients[8] = ingredient;


    }

    public int getIngredientsCount() {
        return ingredientsCount;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", ingredients=" + String.join(", ", ingredients) +
                ", ingredientsCount=" + ingredientsCount +
                '}';
    }
}
