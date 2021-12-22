package ua.iladrien.bakery.web.api.v0.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.iladrien.bakery.web.api.v0.IService;
import ua.iladrien.bakery.web.api.v0.categories.services.CategoryService;
import ua.iladrien.bakery.web.api.v0.products.repos.ProductOptionRepository;
import ua.iladrien.bakery.web.api.v0.products.repos.ProductRepository;
import ua.iladrien.bakery.web.entities.Category;
import ua.iladrien.bakery.web.entities.Product;
import ua.iladrien.bakery.web.entities.ProductOption;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService implements IService<Product> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private CategoryService categoryService;

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public Iterable<Product> findAll(String value) {
        if (value == null || value.isEmpty())
            return productRepository.findAll();
        return productRepository.filter(value);
    }

    public void save(Product product) {
        Set<ProductOption> options = product.getProductOptions();
        if (product.getId() == null) {
            product.setProductOptions(null);
            productRepository.save(product);
            product.setProductOptions(options);
        }
        productOptionRepository.saveAll(options);
        productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public String getPrice(Product product) {
        Set<ProductOption> options = product.getProductOptions();
        if (options == null || options.size() == 0)
            return "";
        else if (options.size() == 1)
            return String.valueOf(options.iterator().next().getPrice());
        else {
            BigDecimal max = options.stream().map(ProductOption::getPrice).max(BigDecimal::compareTo).orElse(null);
            BigDecimal min = options.stream().map(ProductOption::getPrice).min(BigDecimal::compareTo).orElse(null);
            return min + "-" + max;
        }
    }

    public List<Product> findByCategory(Category category) {
        return (List<Product>) productRepository.findByCategory(category);
    }

    public List<Product> findByCategoryAndChildren(Category category) {
        List<Category> allChildren = categoryService.findAllChildren(category);
        return allChildren.stream().map(this::findByCategory).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
