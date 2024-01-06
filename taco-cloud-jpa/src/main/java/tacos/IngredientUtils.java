package tacos;

import tacos.model.Ingredient;

import java.util.Arrays;
import java.util.List;

public class IngredientUtils {
    public static List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = Arrays.asList(
            Ingredient.builder().id("FLTO").name("Flour Tortilla").type(Ingredient.Type.WRAP).build(),
            Ingredient.builder().id("COTO").name("Corn Tortilla").type(Ingredient.Type.WRAP).build(),
            Ingredient.builder().id("GRBF").name("Ground Beef").type(Ingredient.Type.PROTEIN).build(),
            Ingredient.builder().id("CARN").name("Carnitas").type(Ingredient.Type.PROTEIN).build(),
            Ingredient.builder().id("TMTO").name("Diced Tomatoes").type(Ingredient.Type.VEGGIES).build(),
            Ingredient.builder().id("LETC").name("Lettuce").type(Ingredient.Type.VEGGIES).build(),
            Ingredient.builder().id("CHED").name("Cheddar").type(Ingredient.Type.CHEESE).build(),
            Ingredient.builder().id("JACK").name("Monterrey Jack").type(Ingredient.Type.CHEESE).build(),
            Ingredient.builder().id("SLSA").name("Salsa").type(Ingredient.Type.SAUCE).build(),
            Ingredient.builder().id("SRCR").name("Sour Cream").type(Ingredient.Type.SAUCE).build()
        );
        return ingredients;
    }
}
