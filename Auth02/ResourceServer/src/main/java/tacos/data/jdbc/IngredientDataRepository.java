package tacos.data.jdbc;

import org.springframework.data.repository.CrudRepository;
import tacos.model.Ingredient;

import java.util.Optional;

public interface IngredientDataRepository extends CrudRepository<Ingredient, String> {

}
