package pizzeria;

public class Pizza {
    private String name;
    private final String type;
    private final String[] ingredients = new String[7];
    private int ingredientCount = 0;
    private static final String[] allIngredients = {"Tomato paste" , "Cheese" , "Salami" , "Bacon", "Garlic", "Corn" , "Pepperoni", "Olives"};
    // price for each ingredient — index matches allIngredients
    // e.g. allIngredients[0] = "Tomato paste", allPrices[0] = 1.0
    private static final double [] allPrices = {1, 1, 1.5, 1.2, 0.3, 0.7, 0.6, 0.5 };
    private final int quantity;
    //constructor
    public Pizza(String name, String type, int quantity) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
    }
    //getters
    public String getName() {return name;}
    public String getType() {return type;}
    public String[] getIngredients() {return ingredients;}
    public int getQuantity() {return quantity;}
    //setter
    public void setName(String name) {this.name = name;}

    public void addIngredient(String ingredient){
        if(ingredientCount == 7){
            System.out.println("Pizza is full");
            return;//after return do nothing
        }
        // assume ingredient is not in the allowed list
        // loop through allowed ingredients to check if this one is valid
        boolean found = false;
        for(String s: allIngredients){
            if(s.equals(ingredient)){
                found = true;// found a match — ingredient is valid
            }
        }
        if(!found){
            System.out.println("Invalid ingredients");
        }
        // assume ingredient is not already on this pizza
        // loop through current ingredients to check for duplicates
        // skip null slots — calling .equals() on null would crash
        boolean dublicate  = false;
        for(String s : this.ingredients){
            if(s != null && s.equals(ingredient)){
                dublicate = true;// found the same ingredient already added
            }
        }
        if(dublicate){
            System.out.println("Check your order again");
        }
        // only add if ingredient is valid AND not already there
        if (found && !dublicate) {
            ingredients[ingredientCount] = ingredient;// place in next empty slot
            ingredientCount++;// move counter forward for next ingredient
        }
    }
    public double calculatePrices(){
        double total = 1;
        if(type.equals("Calzone")){
            total += 0.5;
        }
        for(String s : ingredients){
            if(s == null) continue;
            // find matching ingredient in allIngredients to get its price
            for (int i = 0; i < allIngredients.length; i++) {
                if(allIngredients[i].equals(s)){
                    total += allPrices[i];// add ingredient price using same index
                }
            }
        }
        return total;
    }
    // static getters — accessible without creating a Pizza object
    // used by Order class to look up ingredient prices
    public static String[] getAllIngredients() {return allIngredients;}
    public static double[] getAllPrices() {return allPrices;}


}
