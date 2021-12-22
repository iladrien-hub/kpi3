package ua.iladrien.bakery.ui.shop;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import ua.iladrien.bakery.ui.Theme;
import ua.iladrien.bakery.ui.admin.components.GridLayout;
import ua.iladrien.bakery.ui.shop.cards.CategoryCard;
import ua.iladrien.bakery.ui.shop.cards.ProductCard;
import ua.iladrien.bakery.web.api.v0.categories.services.CategoryService;
import ua.iladrien.bakery.web.api.v0.products.services.ProductService;
import ua.iladrien.bakery.web.entities.Category;
import ua.iladrien.bakery.web.entities.Product;
import ua.iladrien.bakery.web.entities.ProductOption;

import java.util.List;
import java.util.Set;


@Route(value = "shop/category/:id", layout = ShopLayout.class)
public class CategoryView extends VerticalLayout implements BeforeEnterObserver {

    private final CategoryService categoryService;
    private final ProductService productService;

    private Category category;

    public CategoryView(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Integer id = Integer.valueOf(beforeEnterEvent.getRouteParameters().get("id").orElseThrow());
        category = categoryService.findById(id);
        removeAll();
        if (category != null) {
            initUi();
        }
    }

    private void initUi() {
        setMaxWidth(Theme.CONTAINER_WIDTH, Unit.PIXELS);
        addClassName(Theme.AUTO_MARGIN);

        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.CENTER);

        H1 h1 = new H1(category.getName());
        h1.addClassName(Theme.TEXT_CENTER);
        add(h1);

        String description = category.getDescription();
        if (description != null) {
            H6 h6 = new H6(description);
            h6.addClassName("text-center");
            add(h6);
        }
        initProducts();
        initSubCategories((List<Category>) categoryService.findByParent(category));
    }

    private void initProducts() {
        List<Product> products = productService.findByCategoryAndChildren(category);
        if (products != null && products.size() > 0) {
            GridLayout grid = new GridLayout(4);
            products.forEach(product -> {
                Set<ProductOption> productOptions = product.getProductOptions();
                if (productOptions != null && productOptions.size() > 0)
                    grid.addComponent(new ProductCard(product, productService));
            });
            add(grid);
        }
    }

    private void initSubCategories(List<Category> byParent) {
        if (byParent != null && byParent.size() > 0) {
            H2 h2 = new H2("Related categories");
            add(h2);
            GridLayout grid = new GridLayout(4);
            byParent.forEach(category -> grid.addComponent(new CategoryCard(category)));
            add(grid);
        }
    }

    private Component initCards() {
        return null;
    }
}
