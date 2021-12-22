package ua.iladrien.bakery.web.api.v0.products.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.iladrien.bakery.web.api.v0.categories.services.CategoryService;
import ua.iladrien.bakery.web.api.v0.products.repos.ProductOptionRepository;
import ua.iladrien.bakery.web.api.v0.products.repos.ProductRepository;
import ua.iladrien.bakery.web.entities.Category;
import ua.iladrien.bakery.web.entities.Product;
import ua.iladrien.bakery.web.entities.ProductOption;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceTest {

    @MockBean private ProductRepository productRepository;
    @MockBean private ProductOptionRepository productOptionRepository;
    @MockBean private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Test
    void findAll() {
        productService.findAll("");
        verify(productRepository, times(1)).findAll();

        productService.findAll("asd");
        verify(productRepository, times(1)).filter("asd");
    }

    @Test
    void getPrice() {
        Product product = new Product();
        Assertions.assertThat(productService.getPrice(product)).isEqualTo("");

        ProductOption p1 = new ProductOption();
        p1.setPrice(BigDecimal.valueOf(10));
        product.getProductOptions().add(p1);
        Assertions.assertThat(productService.getPrice(product)).isEqualTo("10");

        ProductOption p2 = new ProductOption();
        p2.setPrice(BigDecimal.valueOf(12.3));
        product.getProductOptions().add(p2);
        Assertions.assertThat(productService.getPrice(product)).isEqualTo("10-12.3");
    }

    @Test
    void findByCategoryAndChildren() {
        when(categoryService.findAllChildren(ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>());
        productService.findByCategoryAndChildren(new Category());
        verify(categoryService, times(1)).findAllChildren(ArgumentMatchers.any());
    }
}