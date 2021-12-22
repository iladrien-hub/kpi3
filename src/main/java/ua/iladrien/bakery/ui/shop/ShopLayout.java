package ua.iladrien.bakery.ui.shop;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import ua.iladrien.bakery.ui.Theme;

@CssImport(value = "./styles/style.css")
@CssImport(value = "./styles/vaadin-app-layout.css", themeFor = "vaadin-app-layout")
public class ShopLayout extends AppLayout {

    public ShopLayout() {
        initHeader();
    }

    private void initHeader() {
        H1 h1 = new H1("MuchSweeter");
        h1.addClassName("logo");
        h1.addClickListener(evt -> UI.getCurrent().navigate("shop"));

        Button cart = new Button("CART");
        cart.addClickListener(evt -> UI.getCurrent().navigate("shop/cart"));

        Div div = new Div();
        div.addClassName("spacer");

        HorizontalLayout header = new HorizontalLayout(h1, div, cart);
        header.setMaxWidth(Theme.CONTAINER_WIDTH, Unit.PIXELS);
        header.addClassName(Theme.AUTO_MARGIN);

        header.addClassName("header");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();

        addToNavbar(header);
    }

}
