package tacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select id, name, type from Ingredient", getIngredientRowMapper());
    }

    private RowMapper<Ingredient> getIngredientRowMapper() {
        return (row, rowNum) -> Ingredient.builder()
                .id(row.getString("id"))
                .name(row.getString("name"))
                .type(Ingredient.Type.valueOf(row.getString("type")))
                .build();
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> list = jdbcTemplate.query("select id, name, type from ingredient where id = ?", getIngredientRowMapper(), id);
        return CollectionUtils.isEmpty(list) ? Optional.empty(): Optional.of(list.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("insert into ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }
}
