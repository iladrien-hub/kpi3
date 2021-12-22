package ua.iladrien.bakery.web.api.v0.products.repos;

import org.springframework.data.repository.CrudRepository;
import ua.iladrien.bakery.web.entities.ProductOption;

public interface ProductOptionRepository extends CrudRepository<ProductOption, Integer> {

}
