package ua.iladrien.bakery.web.api.v0.orders.repos;

import org.springframework.data.repository.CrudRepository;
import ua.iladrien.bakery.web.entities.CartItem;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
}
