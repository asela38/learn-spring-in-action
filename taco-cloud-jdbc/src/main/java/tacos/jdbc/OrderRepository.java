package tacos.jdbc;

import tacos.model.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder tacoOrder);

    void deleteAllOrders();
}
