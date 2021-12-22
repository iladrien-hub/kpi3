package ua.iladrien.bakery.web.api.v0.categories.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.iladrien.bakery.web.api.v0.IService;
import ua.iladrien.bakery.web.api.v0.categories.repos.CategoryRepository;
import ua.iladrien.bakery.web.api.v0.products.services.ProductService;
import ua.iladrien.bakery.web.entities.Category;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements IService<Category> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Iterable<Category> findAll(String value) {
        return categoryRepository.filter(value);
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void save(Category entity) {
        categoryRepository.save(entity);
    }

    public void delete(Category entity) {
        categoryRepository.findByParent(entity).forEach(category -> {
            category.setParent(null);
            save(category);
        });
        productService.findByCategory(entity).forEach(product -> {
            product.setCategory(null);
            productService.save(product);
        });
        categoryRepository.delete(entity);
    }

    public Iterable<Category> findTop() {
        return categoryRepository.findByParentIsNull();
    }

    public Iterable<Category> findByParent(Category category) {
        return categoryRepository.findByParent(category);
    }

    public List<Category> findAllChildren(Category category) {
        List<Category> children = (List<Category>) categoryRepository.findByParent(category);
        List<Category> res = new ArrayList<>();
        res.add(category);
        children.forEach(child -> res.addAll(findAllChildren(child)));
        return res;
    }
}
