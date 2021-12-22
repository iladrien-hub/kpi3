package ua.iladrien.bakery.web.api.v0.categories.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.iladrien.bakery.web.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("from Category where " +
            "lower(name) LIKE lower(concat('%', :filter, '%'))")
    Iterable<Category> filter(@Param("filter") String filter);

    Iterable<Category> findByParent(Category entity);

    Iterable<Category> findByParentIsNull();
}
