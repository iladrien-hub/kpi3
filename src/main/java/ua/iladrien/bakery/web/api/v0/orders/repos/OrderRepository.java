package ua.iladrien.bakery.web.api.v0.orders.repos;

import org.springframework.data.repository.CrudRepository;
import ua.iladrien.bakery.web.entities.OrderData;

public interface OrderRepository extends CrudRepository<OrderData, Integer> {

    Iterable<OrderData> findAllByStatus(OrderData.OrderStatus value);
}
