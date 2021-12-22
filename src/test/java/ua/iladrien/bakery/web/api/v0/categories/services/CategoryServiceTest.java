package ua.iladrien.bakery.web.api.v0.categories.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.iladrien.bakery.web.api.v0.categories.repos.CategoryRepository;
import ua.iladrien.bakery.web.api.v0.products.services.ProductService;
import ua.iladrien.bakery.web.entities.Category;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceTest {

    @MockBean private CategoryRepository categoryRepository;
    @MockBean private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Test
    void findAll() {
        categoryService.findAll();
        verify(categoryRepository, times(1)).findAll();

        categoryService.findAll("Asd");
        verify(categoryRepository, times(1)).filter("Asd");
    }

    @Test
    void findById() {
        categoryService.findById(0);
        verify(categoryRepository, times(1)).findById(0);
    }

    @Test
    void save() {
        Category entity = new Category();
        categoryService.save(entity);
        verify(categoryRepository, times(1)).save(entity);
    }

    @Test
    void delete() {
        Category entity = new Category();
        categoryService.delete(entity);
        verify(categoryRepository, times(1)).delete(entity);
    }

    @Test
    void findTop() {
        categoryService.findTop();
        verify(categoryRepository, times(1)).findByParentIsNull();
    }

    @Test
    void findByParent() {
        Category entity = new Category();
        categoryService.findByParent(entity);
        verify(categoryRepository, times(1)).findByParent(entity);
    }
}