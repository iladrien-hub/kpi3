package ua.iladrien.bakery.web.api.v0.products.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ua.iladrien.bakery.web.entities.Category;
import ua.iladrien.bakery.web.entities.Product;

@Component
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query("from Product where " +
            "lower(name) LIKE lower(concat('%', :filter, '%')) or " +
            "lower(category.name) LIKE lower(concat('%', :filter, '%'))")
    Iterable<Product> filter(@Param("filter") String value);

    Iterable<Product> findByCategory(Category category);
}
