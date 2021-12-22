package ua.iladrien.bakery.ui.shop.cards;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteParam;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.router.RouterLink;
import ua.iladrien.bakery.ui.Theme;
import ua.iladrien.bakery.ui.shop.ProductView;
import ua.iladrien.bakery.ui.shop.components.CoverImage;
import ua.iladrien.bakery.web.api.v0.products.services.ProductService;
import ua.iladrien.bakery.web.entities.Product;

public class ProductCard extends VerticalLayout {


    public ProductCard(Product product, ProductService productService) {
        byte[] img = product.getImage();

        setAlignItems(Alignment.CENTER);
        setMaxWidth("400px");

        String price = productService.getPrice(product);
        RouterLink routerLink = new RouterLink(
                product.getName() + " " + (!price.equals("") ? price + "€" : ""),
                ProductView.class,
                new RouteParameters(new RouteParam("id", String.valueOf(product.getId())))
        );
        routerLink.addClassName(Theme.TEXT_CENTER);
        add(new CoverImage(product.getImage(), product.getName(), 250, 250), routerLink);
    }
}
