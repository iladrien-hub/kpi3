package ua.iladrien.bakery.ui.shop.cards;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import ua.iladrien.bakery.ui.shop.CategoryView;
import ua.iladrien.bakery.ui.shop.components.CoverImage;
import ua.iladrien.bakery.web.entities.Category;

import java.io.ByteArrayInputStream;

public class CategoryCard extends VerticalLayout {

    public CategoryCard(Category category) {
        byte[] img = category.getImage();

        setAlignItems(Alignment.CENTER);
        setMaxWidth("400px");

        RouterLink routerLink = new RouterLink(
                category.getName(),
                CategoryView.class,
                new RouteParameters(new RouteParam("id", String.valueOf(category.getId())))
        );
        add(new CoverImage(category.getImage(), category.getName(), 250, 250), routerLink);
    }
}
