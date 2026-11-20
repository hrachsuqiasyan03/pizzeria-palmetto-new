package am.trainings.palmetto.pizza;

import com.fasterxml.jackson.annotation.JsonKey;

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

        if (ingredients!=null) {
        for (Ingredients ingredient : ingredients) {
            if (ingredient != null) {
                this.ingredients.add(ingredient);
                 }
            }
        }
        this.ingredientsCount = this.ingredients.size();
    }

    @JsonKey
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Ingredients> getIngredients() {
        return new ArrayList<> (ingredients);
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
