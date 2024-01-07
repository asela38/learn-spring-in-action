package tacos;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.data.jdbc.IngredientDataRepository;
import tacos.model.Ingredient;
import tacos.model.IngredientUDT;

import java.util.Optional;

@Component
public class IngredientByIdConverter implements Converter<String, IngredientUDT> {

    private final IngredientDataRepository ingredientRepository;

    public IngredientByIdConverter(IngredientDataRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientUDT convert(String id) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        if (ingredient.isEmpty()) {
            return null;
        }

        return ingredient.map(i -> {
            return new IngredientUDT(i.getName(), i.getType());
        }).get();
    }
}
