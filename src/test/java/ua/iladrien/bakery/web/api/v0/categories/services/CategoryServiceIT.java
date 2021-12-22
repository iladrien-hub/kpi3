package ua.iladrien.bakery.web.api.v0.categories.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ua.iladrien.bakery.web.api.v0.categories.repos.CategoryRepository;
import ua.iladrien.bakery.web.api.v0.products.services.ProductService;
import ua.iladrien.bakery.web.entities.Category;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-dev.properties")
@Sql(scripts = {"/sql/category-before.sql", "/sql/product-before.sql", "/sql/other-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/sql/other-after.sql", "/sql/product-after.sql", "/sql/category-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CategoryServiceIT {

    @Autowired
    private CategoryService categoryService;

    @Test
    void findAllChildren() {
        Category category = categoryService.findById(8);
        List<Category> children = categoryService.findAllChildren(category);

        Assertions.assertThat(children.size()).isEqualTo(2);
    }

    @Test
    void findAllChildrenNegative() {
        Category category = categoryService.findById(9);
        List<Category> children = categoryService.findAllChildren(category);

        Assertions.assertThat(children.size()).isEqualTo(1);
    }
}