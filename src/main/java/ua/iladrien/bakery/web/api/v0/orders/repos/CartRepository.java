package ua.iladrien.bakery.web.api.v0.orders.repos;

import org.springframework.data.repository.CrudRepository;
import ua.iladrien.bakery.web.entities.Cart;
import ua.iladrien.bakery.web.entities.OrderData;

public interface CartRepository extends CrudRepository<Cart, Integer> {
    Cart findBySessionPushIdAndOrderIsNull(String sessionPushId);

    Cart findByOrder(OrderData value);
}
