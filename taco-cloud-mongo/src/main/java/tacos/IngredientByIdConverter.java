package tacos;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.data.jdbc.IngredientDataRepository;
import tacos.model.Ingredient;

import java.util.Optional;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final IngredientDataRepository ingredientRepository;

    public IngredientByIdConverter(IngredientDataRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        return ingredient.orElse(null);
    }
}
