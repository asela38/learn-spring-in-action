package tacos;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private final Map<String, Ingredient> ingredientMap = IngredientUtils.getIngredients().stream()
                                                            .collect(Collectors.toMap(i -> i.getId(), Function.identity(), (i,j) -> j, HashMap::new));

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}
