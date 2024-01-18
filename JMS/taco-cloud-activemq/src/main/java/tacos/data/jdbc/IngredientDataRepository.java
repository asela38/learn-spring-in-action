package tacos.data.jdbc;

import org.springframework.data.repository.CrudRepository;
import tacos.model.Ingredient;

public interface IngredientDataRepository extends CrudRepository<Ingredient, String> {

}
