package tacos.data.jdbc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tacos.model.Taco;

@Repository
public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {
}
