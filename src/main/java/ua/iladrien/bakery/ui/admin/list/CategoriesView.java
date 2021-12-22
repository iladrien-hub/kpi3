package ua.iladrien.bakery.ui.admin.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ua.iladrien.bakery.ui.admin.AdminLayout;
import ua.iladrien.bakery.web.api.v0.categories.services.CategoryService;
import ua.iladrien.bakery.web.entities.Category;

import java.util.Collection;

@Route(value = "categories", layout = AdminLayout.class)
@PageTitle("Categories | Admin")
public class CategoriesView extends VerticalLayout {

    private final TextField filterField = new TextField();
    private final Grid<Category> grid = new Grid<>(Category.class);

    private final CategoryService categoryService;

    public CategoriesView(CategoryService categoryService) {
        this.categoryService = categoryService;
        setSizeFull();

        configureFilter();
        HorizontalLayout horizontalLayout = new HorizontalLayout(filterField, creteAddButton());
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(JustifyContentMode.END);

        initGrid();
        updateGrid();

        add(horizontalLayout, grid);
    }

    private void configureFilter() {
        filterField.setPlaceholder("Type to filter...");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(e -> updateGrid());
    }

    private void updateGrid() {
        grid.setItems((Collection<Category>) categoryService.findAll(filterField.getValue()));
    }

    private void initGrid() {
        grid.setColumns("id", "name");
        grid.setSizeFull();
        grid.addColumn(category -> {
            Category parent = category.getParent();
            return parent != null ? parent.getName() : "";
        }).setHeader("Parent").setSortable(true);
        grid.getColumnByKey("id").setFlexGrow(0);
        grid.getColumns().forEach(c -> c.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(evt -> editCategory(evt.getValue()));
    }

    private void editCategory(Category value) {
        if (value != null) {
            UI.getCurrent().navigate("category/" + value.getId());
        }
    }

    private Component creteAddButton() {
        Button addButton = new Button("+");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.addClickListener(evt -> UI.getCurrent().navigate("category/new"));
        return addButton;
    }
}
