package ua.iladrien.bakery.ui.shop;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ua.iladrien.bakery.ui.admin.components.GridLayout;
import ua.iladrien.bakery.ui.shop.cards.CategoryCard;
import ua.iladrien.bakery.web.api.v0.categories.services.CategoryService;


@Route(value = "shop", layout = ShopLayout.class)
@PageTitle("Home | MuchSweater")
public class HomeView extends VerticalLayout {

    private final CategoryService categoryService;

    public HomeView(CategoryService categoryService) {
        this.categoryService = categoryService;

        setSizeFull();

        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.CENTER);

        H1 sweet_categories = new H1("Sweet Categories");
        sweet_categories.addClassName("text-center");

        add(sweet_categories, initCards());
    }

    private Component initCards() {
        GridLayout grid = new GridLayout(4);
        categoryService.findTop().forEach(category -> grid.addComponent(new CategoryCard(category)));
        return grid;
    }
}
