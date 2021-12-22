package ua.iladrien.bakery.ui.admin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import ua.iladrien.bakery.ui.admin.list.CategoriesView;
import ua.iladrien.bakery.ui.admin.list.OrdersView;
import ua.iladrien.bakery.ui.admin.list.ProductsView;
import ua.iladrien.bakery.ui.shop.HomeView;

@CssImport(value = "./styles/style.css")
@CssImport(value = "./styles/vaadin-app-layout.css", themeFor = "vaadin-app-layout")
public class AdminLayout extends AppLayout {

    public AdminLayout() {
        initHeader();
        initDrawer();
    }

    private Component getRouterLink(String text, Class<? extends Component> navigationTarget) {
        RouterLink link = new RouterLink(text, navigationTarget);
        link.addClassName("drawer-link");
        link.setHighlightCondition(HighlightConditions.sameLocation());
        return link;
    }

    private void initDrawer() {
        addToDrawer(new VerticalLayout(
                getRouterLink("Products", ProductsView.class),
                getRouterLink("Categories", CategoriesView.class),
                getRouterLink("Orders", OrdersView.class)
        ));
    }

    private void initHeader() {
        H1 h1 = new H1("MuchSweeter | Admin");
        h1.addClassName("logo");

        Button to_shop = new Button("TO SHOP");
        to_shop.addClickListener(evt -> UI.getCurrent().navigate("shop"));

        Div div = new Div();
        div.addClassName("spacer");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), h1, div, to_shop);
        header.addClassName("header");
        header.setWidthFull();

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();

        addToNavbar(header);
    }
}
