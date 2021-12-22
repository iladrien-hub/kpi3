package ua.iladrien.bakery.web.api.v0.products.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.iladrien.bakery.web.api.v0.products.repos.ProductOptionRepository;
import ua.iladrien.bakery.web.entities.ProductOption;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductOptionServiceTest {

    @MockBean private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductOptionService productOptionService;

    @Test
    void saveAll() {
        HashSet<ProductOption> productOptions = new HashSet<>();
        productOptionService.saveAll(productOptions);
        verify(productOptionRepository, times(1)).saveAll(productOptions);
    }

    @Test
    void removeAll() {
        HashSet<ProductOption> productOptions = new HashSet<>();
        productOptionService.removeAll(productOptions);
        verify(productOptionRepository, times(1)).deleteAll(productOptions);
    }

    @Test
    void remove() {
        ProductOption option = new ProductOption();
        productOptionService.remove(option);
        verify(productOptionRepository, times(1)).delete(option);
    }
}