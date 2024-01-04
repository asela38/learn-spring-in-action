package tacos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
