package am.trainings.palmetto.pizza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * YAGNI, KISS, DRY
 */
public class Pizza {
    private String name;
    private String type;
    private List<Ingredients> ingredients;
    private int ingredientsCount;

    public Pizza(String name, String type, Ingredients[] ingredients) {
        this.name = name;
        this.type = type;
        this.ingredients = new ArrayList<> ();

        if(ingredients!=null) {
        for (Ingredients ingredient : ingredients) {
            if (ingredient != null) {
                this.ingredients.add(ingredient);
                 }
            }
        }
        this.ingredientsCount = this.ingredients.size();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Ingredients> getIngredients() {
        return new ArrayList<> (ingredients);
    }
    public void addIngredient(Ingredients ingredient) {
        if(ingredient != null) {
            this.ingredients.add(ingredient);
            ingredientsCount++;
        }
    }
    public int getIngredientsCount() {
        return ingredientsCount;
    }

    public double getIngredientPrice() {
        double price  = PizzaType.valueOf(type).getPrice();
        for (Ingredients ingredient : ingredients) {
            price += ingredient.getPrice();
        }
        return price;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", ingredients=" + ingredients +
                ", ingredientsCount=" + ingredientsCount +
                ", ingredientsPrice=" + getIngredientPrice() + "$" +
                '}';
    }
}
