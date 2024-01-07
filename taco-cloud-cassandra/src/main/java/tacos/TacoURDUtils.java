package tacos;

import tacos.model.Ingredient;
import tacos.model.IngredientUDT;

public class TacoURDUtils {
    public static IngredientUDT toIngredientUDT(Ingredient ingredient) {
        return new IngredientUDT(ingredient.getName(), ingredient.getType());
    }
}
