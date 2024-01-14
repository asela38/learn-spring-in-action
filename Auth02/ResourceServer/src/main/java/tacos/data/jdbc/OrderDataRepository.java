package tacos.data.jdbc;

import org.springframework.data.repository.CrudRepository;
import tacos.model.TacoOrder;

public interface OrderDataRepository extends CrudRepository<TacoOrder, Long> {
}
