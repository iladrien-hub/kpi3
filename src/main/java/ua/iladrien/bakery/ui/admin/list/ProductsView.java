package ua.iladrien.bakery.ui.admin.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ua.iladrien.bakery.ui.admin.AdminLayout;
import ua.iladrien.bakery.web.api.v0.categories.services.CategoryService;
import ua.iladrien.bakery.web.api.v0.products.services.ProductService;
import ua.iladrien.bakery.web.entities.Category;
import ua.iladrien.bakery.web.entities.Product;

import java.util.Collection;

@SuppressWarnings("FieldCanBeLocal")
@Route(value = "", layout = AdminLayout.class)
@PageTitle("Products | Admin")
public class ProductsView extends VerticalLayout {

    private final TextField filterField = new TextField();
    private final Grid<Product> grid = new Grid<>(Product.class);

    private final ProductService productService;
    private final CategoryService categoryService;


    public ProductsView(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
        addClassName("content-container");
        setSizeFull();

        configureGrid();
        configureFilter();

        HorizontalLayout horizontalLayout = new HorizontalLayout(filterField, creteAddButton());
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(JustifyContentMode.END);

        add(
                horizontalLayout,
                grid
        );
        updateGrid();
    }

    private Component creteAddButton() {
        Button addButton = new Button("+");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickListener(evt -> UI.getCurrent().navigate("product/new"));
        return addButton;
    }

    private void configureFilter() {
        filterField.setPlaceholder("Type to filter...");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(e -> updateGrid());
    }

    private void updateGrid() {
        grid.setItems((Collection<Product>) productService.findAll(filterField.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("content-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("category");
        grid.setColumns("id", "name");

        grid.getColumnByKey("id").setFlexGrow(0);

        grid.addColumn(productService::getPrice).setFlexGrow(0).setHeader("Price, €")
                .setTextAlign(ColumnTextAlign.CENTER);

        grid.addColumn(product -> {
            Category cat = product.getCategory();
            return cat == null ? "-" : cat.getName();
        }).setHeader("Category");

        grid.getColumns().forEach(c -> c.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editProduct(evt.getValue()));
    }

    private void editProduct(Product value) {
        if (value != null) {
            UI.getCurrent().navigate("product/" + value.getId());
        }
    }

}
